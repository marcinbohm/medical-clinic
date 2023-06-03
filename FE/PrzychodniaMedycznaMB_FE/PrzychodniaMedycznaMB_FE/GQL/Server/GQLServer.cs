using Newtonsoft.Json;
using PrzychodniaMedycznaMB_FE.GQL.Models;
using PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels;
using PrzychodniaMedycznaMB_FE.Pages.Authorization;
using Session = PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels.Session;

namespace PrzychodniaMedycznaMB_FE.GQL.Server
{
    public class GQLServer : IServerHelper
    {
        string BaseURL = null;
        private GraphQLClient graph;
        int TokenValidSeconds = 86400; //24h

        string? GraphQLUrl = null;
        string? LoginUrl = null;
        string? RefreshTokenPath = null;
        string? RefreshLoginUrl = null;
        //string sLoginQuery = "{\"userName\": \"#USERNAME#\",\"password\": \"#PASSWORD#\"}";
        HttpClient client = new HttpClient();

        public GQLServer()
        {
            SetEnv();
        }

        private void SetEnv()
        {
            BaseURL = ProjectVariables.APIBaseURL;
            if (!BaseURL.EndsWith("/"))
            {
                BaseURL += "/";
            }
            GraphQLUrl = BaseURL + ProjectVariables.ApiQueryPath;
            LoginUrl = BaseURL + ProjectVariables.ApiLoginPath;
            RefreshLoginUrl = BaseURL + ProjectVariables.ApiRefreshLoginPath;
            RefreshTokenPath = BaseURL + ProjectVariables.ApiRefreshTokenPath;
            graph = new GraphQLClient(LoginUrl, GraphQLUrl, RefreshLoginUrl, RefreshTokenPath);
        }

        public async Task<LoginInfo> TryLogin(string username, string password)
        {
            LoginInfo result = null;
            if (BaseURL == null)
            {
                return result;
            }
            try
            {
                bool bLogin = await graph.Login(username, password);

                if (bLogin)
                {
                    result = new LoginInfo();
                    // result.LoginId = Guid.NewGuid().ToString();
                    result.accessToken = graph.Token;
                    result.LoginCode = LoginCodes.OK.ToString();
                    //tutaj trzeba bedzie wrzucic dane z drugiego requesta
                    Console.Write("Zalogowano uzytkownika " + username + "\nDane logowania:\n" + result.accessToken);

                    //pobieramy CurrentUser z GraphQLa

                    string sQuery = Queries.QueryGetCurrentUser;
                    Dictionary<string, object> pars = new Dictionary<string, object>();
                    
                    //string sResult = 
                    Data current = await graph.ExecuteQueryAsync<Data>(sQuery, pars);
                    string sJson = JsonConvert.SerializeObject(current);
                    Console.Write("BBB");
                    Console.Write(sJson);
                    if (current.currentUser != null)
                    {
                        pars.Clear();
                        result.Id = current.currentUser.id;
                        pars.Add("userId", result.Id);

                        result.FirstName = current.currentUser.firstName;
                        pars.Add("userFirstName", result.FirstName);

                        result.LastName = current.currentUser.lastName;
                        pars.Add("userLastName", result.LastName);

                        result.EmpUsername = username;
                        result.TokenValidDate = DateTime.Now.AddSeconds(TokenValidSeconds - 300); //5 min krocej niz prawdziwa wartosc 
                        sJson = JsonConvert.SerializeObject(result);

                        Console.Write("AAA");
                        Console.Write(sJson);


                        sQuery = Queries.QueryGetCurrentUserLocationId;
                        pars.Clear();
                        pars.Add("userId", result.Id);
                        Data data = await graph.ExecuteQueryAsync<Data>(sQuery, pars);
                        User? loggedUser = data.user;
                        Console.Write("LocId=" + loggedUser?.locationId);

                        if(loggedUser?.locationId is not null)
                        {
                            ProjectVariables.CurrentUserLocationId = loggedUser?.locationId;
                        }
                        if(loggedUser?.userId is not null)
                        {
                            ProjectVariables.CurrentUserId = loggedUser?.userId;
                        }

                        if (loggedUser?.userType is not null)
                        {
                            ProjectVariables.CurrentUserType = loggedUser?.userType;
                        }

                        var a = 23;
                        
                        /*sQuery = Queries.QueryPractitionerLocations;
                        pars.Clear();
                        pars.Add("practitionerId", result.Id);
                        Data dataPractitioner = await graph.ExecuteQueryAsync<Data>(sQuery, pars);
                        Practitioner? practitioner = dataPractitioner.practitioner;
                        sJson = JsonConvert.SerializeObject(practitioner);
                        Console.Write("Pract=");
                        Console.Write(sJson);
                        result.Practitioner = practitioner;
                        sJson = JsonConvert.SerializeObject(result);
                        Console.Write(sJson);*/

                    }
                }

            }
            catch (Exception ex)
            {
                Console.Write(ex.Message);
            }


            return result;

        }

        public Task<Category?> GetCategoryByCategoryId(int categoryId)
        {
            throw new NotImplementedException();
        }

        public Task<Encounter?> GetEncounterByEncounterId(int encounterId)
        {
            throw new NotImplementedException();
        }

        public Task<EncounterPage?> GetEncounterPageByLocationId(int locationId, int page, int size, SortOrder sortOrder)
        {
            throw new NotImplementedException();
        }

        public Task<Examination?> GetExaminationByExaminationId(int examinationId)
        {
            throw new NotImplementedException();
        }

        public Task<ExaminationPage?> GetExaminationPageByLocationId(int locationId, int page, int size, SortOrder sortOrder)
        {
            throw new NotImplementedException();
        }

        public Task<Location?> GetLocationByLocationId(int locationId)
        {
            throw new NotImplementedException();
        }

        public Task<Permission?> GetPermissionByPermissionId(int permissionId)
        {
            throw new NotImplementedException();
        }

        public Task<Session?> GetSessionBySessionId(int sessionId)
        {
            throw new NotImplementedException();
        }

        public Task<Session?> GetSessionByUserId(int userId)
        {
            throw new NotImplementedException();
        }

        public Task<User?> GetUserByUserId(int userId)
        {
            throw new NotImplementedException();
        }

        public Task<Class?> GetClassByClassId(int classId)
        {
            throw new NotImplementedException();
        }


        #region Category mutations

        public async Task<bool> AddCategory(CategoryInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateCategory;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addCategory is not null)
                    {
                        status = dataForms.addCategory;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdateCategory(int categoryId, CategoryInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("categoryId", categoryId);

                string query = Queries.UpdateCategory;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateCategory is not null)
                    {
                        status = dataForms.updateCategory;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteCategory(int categoryId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("categoryId", categoryId);

                string query = Queries.DeleteCategory;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteCategory is not null)
                    {
                        status = dataForms.deleteCategory;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region Enocunter mutations
        public async Task<bool> AddEncounter(EncounterInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateEncounter;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addEncounter is not null)
                    {
                        status = dataForms.addEncounter;
                        if(status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }                
            }

            return result;
        }

        public async Task<bool> UpdateEncounter(int? encounterId, EncounterInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null && encounterId is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("encounterId", encounterId);

                string query = Queries.UpdateEncounter;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateEncounter is not null)
                    {
                        status = dataForms.updateEncounter;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteEncounter(int encounterId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("encounterId", encounterId);

                string query = Queries.DeleteEncounter;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteEncounter is not null)
                    {
                        status = dataForms.deleteEncounter;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region Examination mutations

        public async Task<bool> AddExamination(ExaminationInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateExamination;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addExamination is not null)
                    {
                        status = dataForms.addExamination;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdateExamination(int examinationId, ExaminationInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("examinationId", examinationId);

                string query = Queries.UpdateExamination;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateExamination is not null)
                    {
                        status = dataForms.updateExamination;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteExamination(int examinationId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("examinationId", examinationId);

                string query = Queries.DeleteExamination;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteExamination is not null)
                    {
                        status = dataForms.deleteExamination;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region Location mutations

        public async Task<bool> AddLocation(LocationInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateLocation;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addLocation is not null)
                    {
                        status = dataForms.addLocation;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdateLocation(int locationId, LocationInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("locationId", locationId);

                string query = Queries.UpdateLocation;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateLocation is not null)
                    {
                        status = dataForms.updateLocation;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteLocation(int locationId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("locationId", locationId);

                string query = Queries.DeleteLocation;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteLocation is not null)
                    {
                        status = dataForms.deleteLocation;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region Permission mutations
        public async Task<bool> AddPermission(PermissionInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreatePermission;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addPermission is not null)
                    {
                        status = dataForms.addPermission;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdatePermission(int permissionId, PermissionInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("permissionId", permissionId);

                string query = Queries.UpdatePermission;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updatePermission is not null)
                    {
                        status = dataForms.updatePermission;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeletePermission(int permissionId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("permissionId", permissionId);

                string query = Queries.DeletePermission;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deletePermission is not null)
                    {
                        status = dataForms.deletePermission;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region Permission mutations

        public async Task<bool> AddSession(SessionInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateSession;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addSession is not null)
                    {
                        status = dataForms.addSession;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdateSession(int sessionId, SessionInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("sessionId", sessionId);

                string query = Queries.UpdateSession;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateSession is not null)
                    {
                        status = dataForms.updateSession;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteSession(int sessionId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("sessionId", sessionId);

                string query = Queries.DeleteSession;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteSession is not null)
                    {
                        status = dataForms.deleteSession;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region User mutations

        public async Task<bool> AddUser(UserInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateUser;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addUser is not null)
                    {
                        status = dataForms.addUser;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdateUser(int userId, UserInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("userId", userId);

                string query = Queries.UpdateUser;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateUser is not null)
                    {
                        status = dataForms.updateUser;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteUser(int userId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("userId", userId);

                string query = Queries.DeleteUser;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteUser is not null)
                    {
                        status = dataForms.deleteUser;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion

        #region Class mutations

        public async Task<bool> AddClass(ClassInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);

                string query = Queries.CreateClass;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.addClass is not null)
                    {
                        status = dataForms.addClass;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> UpdateClass(int classId, ClassInput data)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && data is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("data", data);
                parameters.Add("classId", classId);

                string query = Queries.UpdateClass;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.updateClass is not null)
                    {
                        status = dataForms.updateClass;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<bool> DeleteClass(int classId)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("classId", classId);

                string query = Queries.DeleteClass;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.deleteClass is not null)
                    {
                        status = dataForms.deleteClass;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        #endregion


        public async Task<List<Encounter>> GetEncounterPageByLocationIdAndUserPatient(int? locationId, int page, int size, SortOrder? sortOrder)
        {

            List<Encounter> encounterList = new();
            bool bLogin = await graph.RefreshLoginAsync();

            if (bLogin && locationId is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("locationId", locationId);
                parameters.Add("size", size);
                parameters.Add("page", page);

                string query = Queries.QueryGetDoctorLocationEncountersQuery;
                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if(dataForms.encounterPageByLocationIdAndUserPatient?.content is not null)
                    {
                        encounterList = dataForms.encounterPageByLocationIdAndUserPatient.content;
                    }
                }
            }
            return encounterList;
        }

        public async Task<List<Patient>> GetPatientsByLocationId(int? locationId, int? page, int? size)
        {
            List<Patient> patientList = new();
            bool bLogin = await graph.RefreshLoginAsync();

            if (bLogin && locationId is not null && page is not null && size is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("locationId", locationId);
                parameters.Add("size", size);
                parameters.Add("page", page);

                string query = Queries.QueryGetPatientsByLocationId;
                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.userPageByLocationIdAndUserPatient?.content is not null)
                    {
                        patientList = dataForms.userPageByLocationIdAndUserPatient.content;
                    }
                }
            }
            return patientList;
        }

        public async Task<List<Location>?> GetLocationList()
        {
            List<Location>? locations = new();
            bool bLogin = await graph.RefreshLoginAsync();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                string query = Queries.QueryGetLocations;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.locationList is not null)
                    {
                        locations = dataForms.locationList;
                    }
                }
            }

            return locations;
        }

        public async Task<List<Practitioner>> GetPractitionerByLocationId(int? locationId, int? page, int? size)
        {
            List<Practitioner>? practitionerList = new();
            bool bLogin = await graph.RefreshLoginAsync();

            if (bLogin && locationId is not null && page is not null && size is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("locationId", locationId);
                parameters.Add("size", size);
                parameters.Add("page", page);

                string query = Queries.QueryGetPractitionersByLocationId;
                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.userPageByLocationIdAndUserDoctor?.content is not null)
                    {
                        practitionerList = dataForms.userPageByLocationIdAndUserDoctor.content;
                    }
                }
            }
            return practitionerList;
        }

        public async Task<List<User>?> GetUserList()
        {
            List<User>? userList = new();
            bool bLogin = await graph.RefreshLoginAsync();

            if (bLogin)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();

                string query = Queries.QueryGetUsers;
                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.usersList is not null)
                    {
                        userList = dataForms.usersList;
                    }
                }
            }
            return userList;
        }

        public async Task<bool> ChangePassword(int? userId, string oldPassword, string newPassword)
        {
            bool result = false;

            bool bLogin = await graph.RefreshLoginAsync();
            OperationStatus status = new();

            if (bLogin && userId is not null )
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("userId", userId);
                parameters.Add("oldPassword", oldPassword);
                parameters.Add("newPassword", newPassword);

                string query = Queries.ChangePassword;

                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.resetPassword is not null)
                    {
                        status = dataForms.resetPassword;
                        if (status is not null && status.success.HasValue && status.success.Value == true)
                        {
                            result = true;
                        }
                    }
                }
            }

            return result;
        }

        public async Task<List<Examination>?> GetExaminationPageByEncounterId(int? encounterId, int page, int size)
        {

            List<Examination>? examinationList = new();
            bool bLogin = await graph.RefreshLoginAsync();

            if (bLogin && encounterId is not null)
            {
                Dictionary<string, object> parameters = new Dictionary<string, object>();
                parameters.Add("encounterId", encounterId);
                parameters.Add("size", size);
                parameters.Add("page", page);

                string query = Queries.QueryGetEncounterExaminations;
                Data dataForms = await graph.ExecuteQueryAsync<Data>(query, parameters);
                if (dataForms == null)
                {
                    Console.Write("dataForms is null");
                }
                else
                {
                    if (dataForms.examinationPageByEncounterId?.content is not null)
                    {
                        examinationList = dataForms.examinationPageByEncounterId.content;
                    }
                }
            }
            return examinationList;
        }
    }
}
