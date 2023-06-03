using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using MudBlazor.Services;
using PrzychodniaMedycznaMB_FE;
using PrzychodniaMedycznaMB_FE.GQL;
using PrzychodniaMedycznaMB_FE.GQL.Server;

public class Program
{
    public static void ConfigureServices(IServiceCollection services, IWebAssemblyHostEnvironment hostEnvironment, IConfiguration configuration)
    {
        var baseAddress = hostEnvironment.BaseAddress;
        ProjectVariables.BaseURL = baseAddress;
        IConfigurationSection wizyty = configuration.GetSection(ProjectVariables.PORADNIA_CONFIG_SECTION);
        if (wizyty != null)
        {
            ProjectVariables.APIBaseURL = wizyty.GetValue<string>("BaseAPIURL");
            ProjectVariables.ProxyServerURL = wizyty.GetValue<string>("ProxyServerURL");
        }

    }
    private static async Task Main(string[] args)
    {
        var builder = WebAssemblyHostBuilder.CreateDefault(args);


        builder.RootComponents.Add<App>("#app");
        builder.RootComponents.Add<HeadOutlet>("head::after");

        builder.Services.AddScoped(sp => new HttpClient { BaseAddress = new Uri(builder.HostEnvironment.BaseAddress) });

        builder.Services.AddMudServices();

        builder.Services.AddScoped<IServerHelper, GQLServer>();

       /* builder.Services.AddCors(options =>
        {
            options.AddDefaultPolicy(builder =>
            builder.AllowAnyOrigin().AllowAnyHeader().AllowAnyMethod().SetIsOriginAllowedToAllowWildcardSubdomains());
            //                builder.WithOrigins("*.konsultant-it.pl").AllowAnyMethod().AllowAnyHeader());
        });*/

        await builder.Build().RunAsync();
    }
}