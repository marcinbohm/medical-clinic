namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class SortOrder
    {
        public String? property { get; set; }

        public Direction direction { get; set; }
    }

    public enum Direction
    {
        ASC, DESC
    }
}
