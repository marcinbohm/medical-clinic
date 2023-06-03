using Newtonsoft.Json;

namespace PrzychodniaMedycznaMB_FE.GQL.Models
{
    public class PayloadData
    {
        public Dictionary<string, object> data { get; set; }

        public string ToJson()
        {
            return JsonConvert.SerializeObject(data, new JsonSerializerSettings { DateTimeZoneHandling = DateTimeZoneHandling.Unspecified, NullValueHandling = NullValueHandling.Ignore });
        }
    }
}
