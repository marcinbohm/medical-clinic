namespace PrzychodniaMedycznaMB_FE.GQL
{
    public static class ConnectionChecker
    {
        public static DateTime LastSync = DateTime.MinValue;

        private static bool isConnected = false;

        public static void SetOffline()
        {
            isConnected = false;
        }

        public static async Task<bool> IsOnline()
        {
            TimeSpan elapsedSeconds = DateTime.Now.Subtract(LastSync);

            if (isConnected && (elapsedSeconds.TotalSeconds <= ProjectVariables.ONLINE_TIMEOUT))
            {
                return true;
            }


            LastSync = DateTime.Now;

            string sAddress = ProjectVariables.APIBaseURL + ProjectVariables.ApiPingPath;


            Uri uri = new Uri(sAddress);
            Console.Write($"Testuje adres {uri.AbsoluteUri}");
            HttpClient client = new HttpClient();

            client.Timeout = new TimeSpan(0, 0, 15);


            bool online = false;
            try
            {
                var msg = await client.GetStringAsync(uri);
                if (msg != null)
                {

                    if (msg == "pong")
                    {
                        online = true;
                        LastSync = DateTime.Now;
                    }
                }

            }
            catch (Exception ex)
            {
                int a = 23;
                online = false;
            }

            if (online)
            {
                Console.Write("Praca w trybie online");
            }

            isConnected = online;
            return online;
        }


        /// <summary>
        /// Ta metoda sluzy tylko do celow deweloperskich, zwraca czy jest online czy nie.
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static bool IsOnlineMethod(bool value)
        {
            return value;
        }
    }
}
