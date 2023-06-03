using PrzychodniaMedycznaMB_FE.GQL.Models;
using PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels;

namespace PrzychodniaMedycznaMB_FE.GQL.Server
{
    public interface IServerHelper
    {
        public Task<LoginInfo> TryLogin(string username, string password);

        public Task<Category?> GetCategoryByCategoryId(int categoryId);

        public Task<Encounter?> GetEncounterByEncounterId(int encounterId);

        public Task<EncounterPage?> GetEncounterPageByLocationId(int locationId, int page, int size, SortOrder sortOrder);

        public Task<Examination?> GetExaminationByExaminationId(int examinationId);

        public Task<ExaminationPage?> GetExaminationPageByLocationId(int locationId, int page, int size, SortOrder sortOrder);
        public Task<List<Examination>?> GetExaminationPageByEncounterId(int? encounterId, int page, int size);

        public Task<Location?> GetLocationByLocationId(int locationId);

        public Task<List<Patient>> GetPatientsByLocationId(int? locationId, int? page, int? size);

        public Task<List<Practitioner>> GetPractitionerByLocationId(int? locationId, int? page, int? size);

        public Task<Permission?> GetPermissionByPermissionId(int permissionId);

        public Task<Session?> GetSessionBySessionId(int sessionId);

        public Task<Session?> GetSessionByUserId(int userId);

        public Task<User?> GetUserByUserId(int userId);

        public Task<Class?> GetClassByClassId(int classId);

        public Task<List<Location>?> GetLocationList();

        public Task<List<User>?> GetUserList();

        public Task<List<Encounter>> GetEncounterPageByLocationIdAndUserPatient(int? locationId, int page, int size, SortOrder? sortOrder);

        #region Mutations

        public Task<bool> AddCategory(CategoryInput data);

        public Task<bool> UpdateCategory(int categoryId, CategoryInput data);

        public Task<bool> DeleteCategory(int categoryId);

        public Task<bool> AddEncounter(EncounterInput data);

        public Task<bool> UpdateEncounter(int? encounterId, EncounterInput data);

        public Task<bool> DeleteEncounter(int encounterId);

        public Task<bool> AddExamination(ExaminationInput data);

        public Task<bool> UpdateExamination(int examinationId, ExaminationInput data);

        public Task<bool> DeleteExamination(int examinationId);

        public Task<bool> AddLocation(LocationInput data);

        public Task<bool> UpdateLocation(int locationId, LocationInput data);

        public Task<bool> DeleteLocation(int locationId);

        public Task<bool> AddPermission(PermissionInput data);

        public Task<bool> UpdatePermission(int permissionId, PermissionInput data);

        public Task<bool> DeletePermission(int permissionId);

        public Task<bool> AddSession(SessionInput data);

        public Task<bool> UpdateSession(int sessionId, SessionInput data);

        public Task<bool> DeleteSession(int sessionId);

        public Task<bool> AddUser(UserInput data);

        public Task<bool> UpdateUser(int userId, UserInput data);

        public Task<bool> DeleteUser(int userId);

        public Task<bool> AddClass (ClassInput data);

        public Task<bool> UpdateClass(int classId, ClassInput data);

        public Task<bool> DeleteClass(int classId);

        public Task<bool> ChangePassword(int? userId, String oldPassword, String newPassword);

        #endregion

    }
}
