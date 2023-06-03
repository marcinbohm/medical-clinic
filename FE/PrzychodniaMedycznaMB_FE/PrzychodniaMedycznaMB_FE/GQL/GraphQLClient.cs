using Newtonsoft.Json;
using GraphQL.Client.Http;
using System.Net;
using System.Text;
using PrzychodniaMedycznaMB_FE.GQL.Models;
using GraphQL;
using System.Net.Http.Headers;
using GraphQL.Client.Serializer.Newtonsoft;

namespace PrzychodniaMedycznaMB_FE.GQL
{
    public class GraphQLClient
    {
        private string _token = default!;
        private long TokenTTL = 290; //5 min waznosci tokena

        private DateTime? _tokenExpiration = null;

        HttpClient client = new HttpClient();
        public string Token { get => _token; set => _token = value; }

        public string? RefreshToken { get; set; }


        LoginInfo loginInfo = new();

        private string sLoginUrl = default!;
        private string sRefreshLoginUrl = default!;
        private string sRefreshTokenUrl = default!;
        private string sQueryUrl = default!;

        GraphQLHttpClient graphQLClient;
        public GraphQLClient(string _sLoginUrl, string _sQueryUrl, string _sRefreshLoginUrl, string _sRefreshTokenUrl)
        {
            sLoginUrl = _sLoginUrl;
            sQueryUrl = _sQueryUrl;
            sRefreshLoginUrl = _sRefreshLoginUrl;
            sRefreshTokenUrl = _sRefreshTokenUrl;
            graphQLClient = new GraphQLHttpClient(sQueryUrl, new NewtonsoftJsonSerializer());
            // graphQLClient = new GraphQLHttpClient(sQueryUrl, new GraphQL.Client.Serializer.SystemTextJson.SystemTextJsonSerializer());
            //GraphQL.Client.Serializer.SystemTextJson
            graphQLClient.HttpClient.DefaultRequestVersion = HttpVersion.Version20;
            client.DefaultRequestVersion = HttpVersion.Version20;

        }

        public async Task<bool> RefreshLoginAsync()
        {
            bool bOnline = await ConnectionChecker.IsOnline();

            if (!bOnline)
            {
                return false;
            }
            //token nadal jest wazny
            if (_tokenExpiration > DateTime.Now)
            {
                return true;
            }

            //token jest juz niewazny, ale refreshToken, jeszcze tak.
            if (DateTime.Now < loginInfo.refreshTokenValidTo)
            {
                RefreshTokenPayload payload = new();
                payload.refreshToken = loginInfo.refreshToken;

                //return await Login(sLUsername, sLPassword);
                bool bResult = false;
                (bResult, loginInfo) = await DoRefreshToken(payload);
                if (bResult)
                {
                    return true;
                }
            }

            //a teraz ostatnia proba, sprobuj loginem i haslem sie zalogowac
            if (bOnline)
            {
                string sLUsername = ProjectVariables.CurrentLogin;
                string sLPassword = ProjectVariables.CurrentPassword;
                //return await Login(sLUsername, sLPassword);
                bool bResult = false;
                LoginInfo nfo = new();
                (bResult, nfo) = await LoginWithRefresh(sLUsername, sLPassword);
                return true;
            }
            return false;

        }

        private async Task<(bool, LoginInfo)> DoRefreshToken(RefreshTokenPayload payload)
        {
            bool bOnline = await ConnectionChecker.IsOnline();
            // LoginInfo loginInfo = null;
            bool bResult = false;
            if (bOnline)
            {
                try
                {
                    string sContent = JsonConvert.SerializeObject(payload);
                    //string sContent = sLoginQuery.Replace("#USERNAME#", sUsername).Replace("#PASSWORD#", sPassword);
                    HttpContent content = new StringContent(sContent, Encoding.UTF8, "application/json");
                    HttpResponseMessage response = await client.PostAsync(sRefreshTokenUrl, content);
                    if (response.IsSuccessStatusCode)
                    {
                        bResult = true;
                        string sResponse = await response.Content.ReadAsStringAsync();
                        //loginInfo = new();
                        loginInfo = JsonConvert.DeserializeObject<LoginInfo>(sResponse);
                        Token = loginInfo.accessToken;
                        _tokenExpiration = loginInfo.accessTokenValidTo;
                        RefreshToken = loginInfo.refreshToken;
                        ConnectionChecker.LastSync = DateTime.Now;
                    }
                }
                catch (Exception exs)
                {
                    Console.Write(exs.Message);
                }
            }
            return (bResult, loginInfo);
        }

        public async Task<(bool, LoginInfo)> LoginWithRefresh(string sUsername, string sPassword)
        {
            bool bOnline = await ConnectionChecker.IsOnline();
            // LoginInfo loginInfo = null;
            bool bResult = false;
            if (bOnline)
            {
                try
                {
                    string sLoginQuery = Queries.LoginQuery;
                    string sContent = sLoginQuery.Replace("#USERNAME#", sUsername).Replace("#PASSWORD#", sPassword);
                    HttpContent content = new StringContent(sContent, Encoding.UTF8, "application/json");
                    HttpResponseMessage response = await client.PostAsync(sRefreshLoginUrl, content);
                    if (response.IsSuccessStatusCode)
                    {
                        bResult = true;
                        //fuuuuuuj, ale nie ma refresh tokena
                        ProjectVariables.CurrentLogin = sUsername;
                        ProjectVariables.CurrentPassword = sPassword;
                        _tokenExpiration = DateTime.Now.AddSeconds(TokenTTL);
                        string sResponse = await response.Content.ReadAsStringAsync();
                        //loginInfo = new();
                        loginInfo = JsonConvert.DeserializeObject<LoginInfo>(sResponse);
                        Token = loginInfo.accessToken;
                        RefreshToken = loginInfo.refreshToken;
                        ConnectionChecker.LastSync = DateTime.Now;
                    }
                }
                catch (Exception exs)
                {
                    Console.Write(exs.Message);
                }
            }
            return (bResult, loginInfo);
        }

        public async Task<bool> Login(string sUsername, string sPassword)
        {
            if (String.IsNullOrEmpty(sUsername))
            {
                return false;
            }
            bool bOnline = await ConnectionChecker.IsOnline();

            bool bResult = false;
            if (bOnline)
            {
                LoginInfo nfo = new();

                (bResult, nfo) = await LoginWithRefresh(sUsername, sPassword);
                return bResult;



                try
                {
                    string sLoginQuery = Queries.LoginQuery;
                    string sContent = sLoginQuery.Replace("#USERNAME#", sUsername).Replace("#PASSWORD#", sPassword);
                    HttpContent content = new StringContent(sContent, Encoding.UTF8, "application/json");
                    HttpResponseMessage response = await client.PostAsync(sLoginUrl, content);
                    if (response.IsSuccessStatusCode)
                    {
                        bResult = true;
                        //fuuuuuuj, ale nie ma refresh tokena
                        ProjectVariables.CurrentLogin = sUsername;
                        ProjectVariables.CurrentPassword = sPassword;
                        _tokenExpiration = DateTime.Now.AddSeconds(TokenTTL);
                        Token = await response.Content.ReadAsStringAsync();
                        ConnectionChecker.LastSync = DateTime.Now;
                    }
                }
                catch (Exception exs)
                {
                    Console.Write(exs.Message);
                }
            }
            return bResult;
        }


        /// <summary>
        /// Wykonuje asynchroniznie zapytanie do serwera GraphQL, jesli trzeba probuje sie zal
        /// </summary>
        /// <typeparam name="T">Typ zwracanego zapytania</typeparam>
        /// <param name="sQuery">Zapytanie graphQL</param>
        /// <param name="parameters">parametry do przekazania do zapytania w formie slownika</param>
        /// <returns>Wynik zapytania rzutowany na tym </returns>
        public async Task<T> ExecuteQueryAsync<T>(string sQuery, Dictionary<string, object> parameters)
        {
            int a = 23;
            T sResult = default(T)!;
            //zserializuj parametry
            string sParameters = JsonConvert.SerializeObject(parameters, new JsonSerializerSettings
            {
                DateTimeZoneHandling = DateTimeZoneHandling.Unspecified,
                NullValueHandling = NullValueHandling.Ignore
            });

            //wykonaj requesta
            sResult = await ExecuteQueryAsync<T>(sQuery, sParameters);
            return sResult;
        }

        /// <summary>
        /// Wykonuje asynchroniznie zapytanie do serwera GraphQL, jesli trzeba probuje sie zal
        /// </summary>
        /// <typeparam name="T">Typ zwracanego zapytania</typeparam>
        /// <param name="sQuery">Zapytanie graphQL</param>
        /// <param name="parameters">parametry do przekazania do zapytania w formie JSONa</param>
        /// <returns>Wynik zapytania rzutowany na tym </returns>
        internal async Task<GraphQLResponse<T>> ExecuteQueryAsyncInternal<T>(string sQuery, string sParameters)
        {

            //utworz nowego requesta z parametrami
            //sQuery = sQuery.Replace("\t", " ").Replace("\n", " ");
            var request = new GraphQLRequest(sQuery, sParameters);

            //dodaj autoryzacje  
            AuthenticationHeaderValue auth = new AuthenticationHeaderValue("Bearer", Token);
            graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = auth;
            //breakpoint
            int a = 23;
            //wykonaj zapytanie           
            var graphQLResponse = await graphQLClient.SendQueryAsync<T>(request);
            ConnectionChecker.LastSync = DateTime.Now;
            return graphQLResponse;
        }
           

        public async Task<T> ExecuteQueryAsync<T>(string sQuery, string sParameters)
        {
            string sResult = null;
            string sContent = sQuery;
            try
            {
                int a = 23;
                if (!(await RefreshLoginAsync()))
                {
                    bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                    if (!bLogged)
                    {
                        return default(T)!;
                    }
                }
                Console.Write( $"sQuery={sQuery} params={sParameters}");
                var graphQLResponse = await ExecuteQueryAsyncInternal<T>(sQuery, sParameters);
                _tokenExpiration = DateTime.Now.AddSeconds(TokenTTL);
                //  MyConsole.Log(LogLevel.Debug, "Raw data:");
                //   MyConsole.Log(LogLevel.Debug, graphQLResponse.Data.ToString());
                return graphQLResponse.Data;

            }
            catch (Exception ex)
            {
                if (ex is GraphQLHttpRequestException)
                {
                    GraphQLHttpRequestException exg = (ex as GraphQLHttpRequestException);
                    Console.Write("Blad GraphQLClient," + ex.Message);
                    if (exg.StatusCode == System.Net.HttpStatusCode.Forbidden)
                    {
                        Console.Write("Blad dostepu 403, logowanie powtorne");
                        bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                        if (!bLogged)
                        {
                            Console.Write("Powtorne logowanie nieudane");
                            return default(T)!;
                        }
                        Console.Write("Podjecie drugiej proby");
                        Console.Write($"sQuery2={sQuery} params={sParameters}");
                        var graphQLResponse2 = await ExecuteQueryAsyncInternal<T>(sQuery, sParameters);
                        _tokenExpiration = DateTime.Now.AddSeconds(TokenTTL);
                        return graphQLResponse2.Data;
                    }
                }
                Console.Write(ex.Message);

            }



            return default(T)!;

            //return sResult;
        }

        /// <summary>
        /// Wykonuje asynchroniznie mytation do serwera GraphQL, jesli trzeba probuje sie zal
        /// </summary>
        /// <typeparam name="T">Typ zwracanego zapytania</typeparam>
        /// <param name="sQuery">Zapytanie graphQL</param>
        /// <param name="parameters">parametry do przekazania do zapytania w formie slownika</param>
        /// <returns>Wynik zapytania rzutowany na tym </returns>
        public async Task<T> ExecuteMutationAsync<T>(string query, Dictionary<string, object> parameters)
        {
            // przygotuj dane w odpowiednim formacie
            PayloadData dane = new PayloadData();
            query = query.Replace("\t", " ").Replace("\n", " ");
            dane.data = parameters;
            // wykonaj mutation
            try
            {
                if (_tokenExpiration > DateTime.Now)
                {
                    bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                    if (!bLogged)
                    {
                        return default(T)!;
                    }
                    Console.Write($"sQuery={query} params={parameters}");
                    var request = new GraphQLRequest(query, dane);
                    graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", Token);
                    var graphQLResponse = await graphQLClient.SendQueryAsync<T>(request);
                    var responseData = graphQLResponse.Data;

                    return responseData;
                }
            }
            catch (Exception ex)
            {
                if (ex is GraphQLHttpRequestException)
                {
                    GraphQLHttpRequestException exg = ex as GraphQLHttpRequestException;
                    if (exg.StatusCode == System.Net.HttpStatusCode.Forbidden)
                    {
                        bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                        if (!bLogged)
                        {
                            return default(T)!;
                        }
                        var request2 = new GraphQLRequest(query, dane);
                        graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", Token);
                        var graphQLResponse2 = await graphQLClient.SendQueryAsync<T>(request2);
                        return graphQLResponse2.Data;
                    }
                }
                Console.Write(ex.Message);
            }
            return default(T)!;
        }


        public async Task<T> ExecuteMutationAsync<T>(string query, object parameters)
        {
            // przygotuj dane w odpowiednim formacie
            query = query.Replace("\t", " ").Replace("\n", " ");
            // wykonaj mutation
            try
            {
                if (_tokenExpiration > DateTime.Now)
                {
                    bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                    if (!bLogged)
                    {
                        return default(T)!;
                    }
                    Console.Write($"sQuery={query} params={parameters}");
                    var request = new GraphQLRequest(query, parameters);
                    graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", Token);
                    var graphQLResponse = await graphQLClient.SendQueryAsync<T>(request);
                    var responseData = graphQLResponse.Data;

                    return responseData;
                }
            }
            catch (Exception ex)
            {
                if (ex is GraphQLHttpRequestException)
                {
                    GraphQLHttpRequestException exg = ex as GraphQLHttpRequestException;
                    if (exg.StatusCode == System.Net.HttpStatusCode.Forbidden)
                    {
                        bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                        if (!bLogged)
                        {
                            return default(T)!;
                        }
                        var request2 = new GraphQLRequest(query, parameters);
                        graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", Token);
                        var graphQLResponse2 = await graphQLClient.SendQueryAsync<T>(request2);
                        return graphQLResponse2.Data;
                    }
                }
                Console.Write(ex.Message);
            }
            return default(T)!;
        }

        public async Task<T> ExecuteMutationAsync<T>(string query, string parameters)
        {
            // przygotuj dane w odpowiednim formacie
            query = query.Replace("\t", " ").Replace("\n", " ");
            // wykonaj mutation
            try
            {
                if (_tokenExpiration > DateTime.Now)
                {
                    bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                    if (!bLogged)
                    {
                        return default(T)!;
                    }
                    Console.Write($"sQuery={query} params={parameters}");
                    var request = new GraphQLRequest(query, parameters);
                    request.Variables = parameters;
                    graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", Token);
                    var graphQLResponse = await graphQLClient.SendQueryAsync<T>(request);
                    var responseData = graphQLResponse.Data;

                    return responseData;
                }
            }
            catch (Exception ex)
            {
                if (ex is GraphQLHttpRequestException)
                {
                    GraphQLHttpRequestException exg = ex as GraphQLHttpRequestException;
                    if (exg.StatusCode == System.Net.HttpStatusCode.Forbidden)
                    {
                        bool bLogged = await Login(ProjectVariables.CurrentLogin, ProjectVariables.CurrentPassword);
                        if (!bLogged)
                        {
                            return default(T)!;
                        }
                        var request2 = new GraphQLRequest(query, parameters);
                        graphQLClient.HttpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", Token);
                        var graphQLResponse2 = await graphQLClient.SendQueryAsync<T>(request2);
                        return graphQLResponse2.Data;
                    }
                }
                Console.Write(ex.Message);
            }
            return default(T)!;
        }
    }
}
