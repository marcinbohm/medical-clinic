using MudBlazor;

namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Permission: PermissionInput
    {
        public Category? category { get; set; }
        public Class? permissionClass { get; set; }
    }

    public class PermissionPage
    {
        public List<Permission>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class PermissionInput
    {
        public int permissionId { get; set; }
        public int? categoryId { get; set; }
        public Boolean? allowRead { get; set; }
        public Boolean? allowAdd { get; set; }
        public Boolean? allowModify { get; set; }
        public Boolean? allowDelete { get; set; }
        public int? classId { get; set; }
    }
}
