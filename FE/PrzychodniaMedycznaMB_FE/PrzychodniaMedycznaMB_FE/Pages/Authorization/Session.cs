namespace PrzychodniaMedycznaMB_FE.Pages.Authorization
{
    public static class Session
    {
        private static Dictionary<string, object> dict = new Dictionary<string, object>();
      
        public static Dictionary<string, object> Dictionary
        {
            get { return Session.dict; }
            set { Session.dict = value; }
        }

        public static void setParameter(String parameterName, Object value)
        {
            Session.dict[parameterName] = value;
        }

        public static void unsetParameter(String parameterName)
        {
            Session.dict.Remove(parameterName);
        }

        public static bool containsParameter(String parameterName)
        {
            if (Session.dict.ContainsKey(parameterName)) return true;
            return false;
        }

        public static Object getValue(String parameterName)
        {
            return Session.dict[parameterName];
        }
    }
}
