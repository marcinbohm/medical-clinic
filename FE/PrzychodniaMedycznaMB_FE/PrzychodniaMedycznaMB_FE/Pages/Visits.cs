using Microsoft.AspNetCore.Components;
using MudBlazor;
using PrzychodniaMedycznaMB_FE.GQL;
using PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels;
using PrzychodniaMedycznaMB_FE.Pages.Dialogs;

namespace PrzychodniaMedycznaMB_FE.Pages
{

    public partial class Visits
    {
        #region Table

        private MudTable<Encounter>? table = default!;
        private IEnumerable<Encounter> pagedData = default!;
        private int totalItems;
        private string searchString = "";
        private Encounter selectedItem1 = default!;

        private string infoFormat = "{first_item}-{last_item} z {all_items}";
        private HashSet<Encounter> selectedItems = new HashSet<Encounter>();

        private async Task<TableData<Encounter>> ServerReload(TableState state)
        {
            IEnumerable<Encounter> data = await _serverHelper.GetEncounterPageByLocationIdAndUserPatient(ProjectVariables.CurrentUserLocationId, 0, 100, null);
            await Task.Delay(100);
            data = data.Where(element =>
            {
                if (string.IsNullOrWhiteSpace(searchString))
                    return true;
                if (element.patient?.firstname is not null && element.patient.firstname.Contains(searchString, StringComparison.OrdinalIgnoreCase))
                    return true;
                if (element.patient?.lastname is not null && element.patient.lastname.Contains(searchString, StringComparison.OrdinalIgnoreCase))
                    return true;
                if (element.location?.name is not null && element.location.name.Contains(searchString, StringComparison.OrdinalIgnoreCase))
                    return true;
                if (element.dateFrom is not null && element.dateFrom.Value.ToString("dd-MM-yyyy hh:mm").Contains(searchString, StringComparison.OrdinalIgnoreCase))
                    return true;
                if (Convert.ToString(element.encounterId).Contains(searchString, StringComparison.OrdinalIgnoreCase))
                    return true;
                return false;
            }).ToArray();
            totalItems = data.Count();

            switch (state.SortLabel)
            {
                case "ident":
                    data = data.OrderByDirection(state.SortDirection, o => o.encounterId);
                    break;

                case "poradnia":
                    data = data.OrderByDirection(state.SortDirection, o => o.location?.name);
                    break;

                case "pacjent":
                    data = data.OrderByDirection(state.SortDirection, o => o.patient?.lastname);
                    break;

                case "termin":
                    data = data.OrderByDirection(state.SortDirection, o => o.dateFrom);
                    break;
            }

            pagedData = data.Skip(state.Page * state.PageSize).Take(state.PageSize).ToArray();
            return new TableData<Encounter>() { TotalItems = totalItems, Items = pagedData };
        }

        private void OnSearch(string text)
        {
            searchString = text;
            table.ReloadServerData();
        }

        private IEnumerable<Encounter> Elements = new List<Encounter>();

        #endregion
        private async Task OpenDialog()
        {

            //var parameters = new DialogParameters { ["server"]= 'a' };
            var options = new DialogOptions { CloseOnEscapeKey = true, MaxWidth = MaxWidth.Medium, FullWidth = true };

            // var dialog = await Dialog.ShowAsync<AddVisit>("Zaplanuj wizytę", parameters, options);
            var dialog = await Dialog.ShowAsync<AddVisit>("Zaplanuj wizytę", options);

            var result = await dialog.Result;

            if (!result.Canceled)
            {
                await ShowAlert((bool)result.Data);
                await Task.Delay(500);
                await table.ReloadServerData();
            }
        }

        private async Task ShowAlert(bool result)
        {
            Snackbar.Clear();
            Snackbar.Configuration.PositionClass = Defaults.Classes.Position.BottomLeft;
            Snackbar.Configuration.SnackbarVariant = Variant.Filled;

            if (result)
            {
                Snackbar.Add("Zaplanowano nową wizytę", Severity.Success);
            }
            else
            {
                Snackbar.Add("Nie udało się zaplanować wizyty", Severity.Error);
            }

            await Task.Delay(500);
        }
    }
}
