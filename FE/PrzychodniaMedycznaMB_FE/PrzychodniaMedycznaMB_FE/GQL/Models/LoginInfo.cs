
using System;
using System.Linq;
using System.IO;
using System.Text;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;
using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations.Schema;

namespace PrzychodniaMedycznaMB_FE.GQL.Models
{

    public partial class LoginInfo 
    {
        private string _LoginId;


        public LoginInfo()
        {
            //base();
            LoginId = Guid.NewGuid().ToString();
        }

        [Key]
        [JsonProperty("LoginId")]
        public string LoginId { get; set; }


        [JsonProperty("loginCode")]
        public string? LoginCode { get; set; }

        public DateTime? TokenValidDate { get; set; }


        [JsonProperty("accessToken")]
        public string? accessToken { get; set; }

        [JsonProperty("accessTokenValidTo")]
        public DateTime accessTokenValidTo { get; set; }


        public int? iExpiresIn = null;

        [JsonProperty("expiresIn")]
        public int? expiresIn
        {
            get
            {
                return iExpiresIn;
            }
            set
            {
                iExpiresIn = value;
                if (value == null)
                {
                    iExpiresIn = 300;
                }
                accessTokenValidTo = DateTime.Now.AddSeconds(Convert.ToDouble(iExpiresIn));
            }
        }

        [JsonProperty("refreshToken")]
        public string? refreshToken { get; set; }

        public int? iRefreshExpiresIn = null;


        [JsonProperty("refreshExpiresIn")]
        public int? refreshExpiresIn
        {
            get
            {
                return iRefreshExpiresIn;
            }
            set
            {
                iRefreshExpiresIn = value;
                if (value == null)
                {
                    iRefreshExpiresIn = 1800;
                }
                refreshTokenValidTo = DateTime.Now.AddSeconds(Convert.ToDouble(iRefreshExpiresIn));
            }
        }


        [JsonProperty("refreshTokenValidTo")]
        public DateTime refreshTokenValidTo { get; set; }

        //[NotMapped] // To tak nie może być jak coś tylko do testów
        [JsonProperty("userHash")]
        public string UserPasswordHash { get; set; }


        /// <summary>
        /// Returns the JSON string presentation of the object
        /// </summary>
        /// <returns>JSON string presentation of the object</returns>
        public new string ToJson()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }


        [JsonProperty("id")]
        public string? Id { get; set; }


        [JsonProperty("lastName")]
        public string? LastName { get; set; }


        [JsonProperty("firstName")]
        public string? FirstName { get; set; }

        [JsonProperty("empUsername")]
        public string? EmpUsername { get; set; }

    }
}
