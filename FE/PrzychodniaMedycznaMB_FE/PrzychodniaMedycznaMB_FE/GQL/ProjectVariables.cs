using PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels;
using PrzychodniaMedycznaMB_FE.Pages.Authorization;
using System.Text.Json.Serialization;
using Session = PrzychodniaMedycznaMB_FE.Pages.Authorization.Session;

namespace PrzychodniaMedycznaMB_FE.GQL
{
    public static class ProjectVariables
    {
        #region Attributes

        private static Encounter CurrentEncounter = default!;
        public static void setCurrentEncounter(Encounter currentEncounter)
        {
            CurrentEncounter = currentEncounter;
        }
        public static Encounter getCurrentEncounter()
        {
            return CurrentEncounter;
        }

        #endregion

        #region URLe

        public static string UsersURL
        {
            get
            {
                return "/users";// + "?v=" + ProjectVariables.Version;
            }
        }

        public static string PatientsURL
        {
            get
            {
                return "/patients";// + "?v=" + ProjectVariables.Version;
            }
        }

        public static string VisitDetailsURL
        {
            get
            {
                return "/visits_details";// + "?v=" + ProjectVariables.Version;
            }
        }

        public static string PlannedVisitsURL
        {
            get
            {
                return "/visits";// + "?v=" + ProjectVariables.Version;
            }
        }

        public static string LoginPageURL
        {
            get
            {
                return "/";// + "?v=" + ProjectVariables.Version;
            }
        }

        #region Klucze sesji
        public static string KEY_CURRENT_USER { get { return "CURRENT_USER"; } }

        #endregion

        #endregion

        public static string ApiLoginPath { get { return "auth/loginUser"; } }
        public static string ApiQueryPath { get { return "graphql"; } }

        public static string ApiRefreshLoginPath { get { return "auth/loginUser"; } }
        public static string ApiRefreshTokenPath { get { return "auth/refreshToken"; } }
        public static string ApiPingPath { get { return "ping"; } }
        private static string _BaseUrl = "http://192.168.1.54:8080/";
        private static string _AppUrl = null;

        /// <summary>
        /// Adres bazowy do API HISa
        /// </summary>
        public static string APIBaseURL
        {
            get { return _BaseUrl; }
            set { _BaseUrl = value; }
        }


        /// <summary>
        /// Adres bazowy aplikacji
        /// </summary>
        public static string BaseURL
        {
            get
            {
                return _AppUrl;
            }
            set { _AppUrl = value; }
        }

        public static string CurrentLogin { get; set; } 

        public static string CurrentPassword { get; set; }

        public static int? CurrentUserLocationId { get; set; }

        public static int? CurrentUserId { get; set; }

        public static int? CurrentUserType { get; set; }

        /// <summary>
        /// liczba sekund przez jaka jest wazne ostatnie sprawdzenie online
        /// </summary>
        public static int ONLINE_TIMEOUT
        {
            get
            {
                return 2;
            }
        }

        public static string ProxyServerURL { get; set; }

        public static string PORADNIA_CONFIG_SECTION { get { return "PoradniaConfig"; } }

        public static bool Logout()
        {
            ProjectVariables.CurrentLogin = null;
            ProjectVariables.CurrentPassword = null;
            if (Session.containsParameter(KEY_CURRENT_USER))
            {
                Session.unsetParameter(KEY_CURRENT_USER);
            }
            return true;
        }
    }



    [JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
    public enum LoginCodes
    {
        /// <summary>
        /// OK - logowanie poprawne
        /// </summary>
        OK,
        /// <summary>
        /// OK_WARN - logowanie poprawne ALE (i szczegoly w loginMessage np. niedlugo wygasnie haslo)
        /// </summary>
        OK_WARN,
        /// <summary>
        /// OK_INFO - logowanie poprawne, dodatkowe informacje. np. aktualne info od dyrekcji, prosba o sprawdzenie konsultacji itp.
        /// </summary>
        OK_INFO,
        /// <summary>
        /// LOGIN_INCORRECT - login lub haslo niepoprawne
        /// </summary>
        LOGIN_INCORRECT,
        /// <summary>
        /// ERROR - inny nieznany blad
        /// </summary>
        ERROR,
        /// <summary>
        /// ACCOUNT_LOCKED - konto zablokowane.
        /// </summary>
        ACCOUNT_LOCKED,
        /// <summary>
        /// PAYLOAD_IS_NULL - przeslano pusty/niemozliwy do odszyfrowania ciag znakow
        /// </summary>
        PAYLOAD_IS_NULL,
        /// <summary>
        /// WRONG_AUTH_SERVER - wybrano niewlasciwy serwer autoryzacyjny, uzykownik nie istnieje/lub dane pracownika nie zgadzaja sie  z loginem.
        /// </summary>
        WRONG_AUTH_SERVER
    }
}
