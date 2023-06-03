namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Class : ClassInput
    {
        public List<Permission>? classPermissionList { get; set; }
        public List<User>? classUserSet { get; set; }

    }

    public class ClassPage
    {
        public List<Class>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class ClassInput
    {
        public int classId { get; set; }
        public String? classCode { get; set; }
        public String? name { get; set; }
        public String? description { get; set; }
        public Boolean? active { get; set; }
        public Boolean? adminClass { get; set; }

    }
}
