namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class OperationStatus
    {
        public String? targetClassName { get; set; }
        public String? operationName { get; set; }
        public Boolean? success { get; set; }
        public int? recordId { get; set; }
        public List<String>? msgList { get; set; }
    }
}
