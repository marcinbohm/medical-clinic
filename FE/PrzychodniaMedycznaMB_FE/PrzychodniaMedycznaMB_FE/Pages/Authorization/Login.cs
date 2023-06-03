using Microsoft.AspNetCore.Components;
using PrzychodniaMedycznaMB_FE.GQL;
using PrzychodniaMedycznaMB_FE.GQL.Models;
using PrzychodniaMedycznaMB_FE.GQL.Server;
using System.ComponentModel.DataAnnotations;
using System.Web;

namespace PrzychodniaMedycznaMB_FE.Pages.Authorization
{
    public partial class Login
    {
        private LoginInfo currentUser = default!;

        [Required]
        public string Username { get; set; } = default!;

        [Required]
        public string Password { get; set; } = default!;

        public bool WrongLogin { get; set; } = false;

        public bool IsLoggedIn { get; set; } = false;

        [Inject]
        public NavigationManager NavigationManager2 { get; set; } = default!;

        private readonly IServerHelper _serverHelper = default!;

        [Inject]
        public IServerHelper serverHelper
        {
            get { return _serverHelper; }
            init { _serverHelper = value; }
        }


        private readonly IServiceProvider _provider = default!;

        [Inject]
        public IServiceProvider provider
        {
            get { return _provider; }
            init
            {
                _provider = value;

            }
        }

        public Login()
        {

        }


        public async Task<(bool, LoginInfo?)> CheckLogin(string username, string password)
        {
            if (_serverHelper is null)
            {
                Console.Write("IServerHelper provider not initialized");
                return (false, null);
            }
            LoginInfo result = await _serverHelper.TryLogin(username, password);
            bool bResult = false;
            if (result != null)
            {
                   bResult = (result.LoginCode == LoginCodes.OK.ToString() ||
                   result.LoginCode == LoginCodes.OK_WARN.ToString() ||
                   result.LoginCode == LoginCodes.OK_INFO.ToString());

            }
            return (bResult, result);
        }


        public async Task<bool> TryToLogin()
        {
            (bool bResult, LoginInfo? login) = await CheckLogin(Username, Password);
            if (bResult)
            {
                string sUserPasswordHash = Hasher.GetHash(Username + Password);
                login.UserPasswordHash = sUserPasswordHash;
                Session.setParameter(ProjectVariables.KEY_CURRENT_USER, login); //dostep w pamieci, 
                WrongLogin = false;
            }

            WrongLogin = !bResult;
            IsLoggedIn = bResult;

            return bResult;
        }

       
    }
}
