using PrzychodniaMedycznaMB_FE.GQL;
using PrzychodniaMedycznaMB_FE.Pages.Authorization;

namespace PrzychodniaMedycznaMB_FE.Shared
{
    public partial class MainLayout
    {
        public bool CheckForLogin()
        {
            Console.Write("Sprawdzenie czy zalogowany");
            if (!Session.containsParameter(ProjectVariables.KEY_CURRENT_USER))
            {
                RedirectToLoginPage();
                return false;//i tak tu nie dotrze.
            }
            //klucz jest ,ale czy nie ma tam nulla pod kluczem?
            var currentUser = Session.getValue(ProjectVariables.KEY_CURRENT_USER);
            if (currentUser is null)
            {
                RedirectToLoginPage();
                return false;
            }
            Console.Write("Zalogowany, mozna dzialac");
            return true;
        }

        private void RedirectToLoginPage()
        {
            string sUri = navManager.Uri;
            Uri uri = new Uri(sUri);
            string sPath = uri.LocalPath;
            Console.Write($"Niezalogowany, lecimy do {ProjectVariables.LoginPageURL + "?forward=" + sPath}");

            navManager.NavigateTo(ProjectVariables.LoginPageURL + "?forward=" + sPath);
        }
    }
}
