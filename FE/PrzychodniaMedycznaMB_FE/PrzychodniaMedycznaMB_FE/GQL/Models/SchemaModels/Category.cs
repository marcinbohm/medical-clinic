namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Category
    {
        public int categoryId { get; set; }
        public String? code { get; set; }
        public String? description { get; set; }
        public String? helpDescription { get; set; }
    }

    public class CategoryPage
    {
        public List<Category>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class CategoryInput
    {
        public int categoryId { get; set; }
        public String? code { get; set; }
        public String? description { get; set; }
        public String? helpDescription { get; set; }
    }
}
