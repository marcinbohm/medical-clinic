using System;

namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Patient : User
    {
    }

    public class Practitioner : User
    {
    }

    public class User : UserInput
    {
        public Location? location { get; set; }
    }

    public class PatientPage
    {
        public List<Patient>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }

    }

    public class PractitonerPage
    {
        public List<Practitioner>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }

    }

    public class UserPage
    {
        public List<User>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }

    }

    public class UserInput
    {
        public int? userId { get; set; }
        public int? locationId { get; set; }
        public String? firstname { get; set; }
        public String? lastname { get; set; }
        public String? email { get; set; }
        public String? login { get; set; }
        public String? password { get; set; }
        public Boolean? active { get; set; }
        public Boolean? blocked { get; set; }
        public DateTime? expireAccountDate { get; set; }
        public DateTime? expirePasswordDate { get; set; }
        public DateTime? lastLoginTime { get; set; }
        public int userType { get; set; }
    }



}
