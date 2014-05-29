using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace visimob_desktop
{
    /// <summary>
    /// Interaction logic for ImovelLista.xaml
    /// </summary>
    public partial class ImovelLista : Window
    {
        SQLiteDatabase db;
        DataTable info;
        string url2 = "";
        public ImovelLista(string url)
        {
            InitializeComponent();
            Application.Current.MainWindow.WindowState = WindowState.Maximized;
            url2 = url;
            RefreshTable();
        }
        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Editar_Click(object sender, RoutedEventArgs e)
        {
            if (tabelaImoveis.SelectedItem==null)
                MessageBox.Show("Escolha um imovel para editar");
            else
            {
                int indice = tabelaImoveis.SelectedIndex;
                EditarImovel edt;
                edt = new EditarImovel(indice, url2);
                edt.Show();
            }
        }

        private void RefreshTable()
        {
            db = new SQLiteDatabase(url2);
            info = db.GetDataTable("SELECT * FROM Imoveis");
            tabelaImoveis.ItemsSource = info.DefaultView;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            RefreshTable();
        }

        private void tabelaImoveis_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            if (tabelaImoveis.SelectedItem != null)
            {
                int indice = tabelaImoveis.SelectedIndex;
                EditarImovel edt;
                edt = new EditarImovel(indice, url2);
                edt.Show();
            }
        }
    }
}
