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
    /// Interaction logic for Imovel.xaml
    /// </summary>
    public partial class Imovel : Window
    {
        SQLiteDatabase db;
        string url2, endereco2;
        DataTable info, campoNome;
        List<string> listaComodos, listaItens;
        public Imovel(string url, string endereco)
        {
            listaComodos = new List<string>();
            listaItens = new List<string>();
            db = new SQLiteDatabase(url);
            url2 = url;
            endereco2 = endereco;
            info = db.GetDataTable("SELECT ComodoNome FROM Comodos WHERE ComodoEndereco IS '" + endereco + "'");
            InitializeComponent();
            detalhes.Visibility = System.Windows.Visibility.Hidden;
            foreach(DataRow row in info.Rows)
            {
                TabItem tb = new TabItem();
                tb.Header = FormataString.Formata(row.ItemArray[0].ToString());
                Tabulacao.Items.Add(tb);
                listaComodos.Add(row.ItemArray[0].ToString());
            }
            //Tabulacao.ItemsSource = col;
            this.WindowState = WindowState.Maximized;
        }

        private void carregaItens(string comodoNome)
        {
            db = new SQLiteDatabase(url2);
            campoNome = db.GetDataTable("SELECT Campo FROM Campos WHERE ImovelEnd IS '" + endereco2 + "' AND ComodoCampo IS '" + comodoNome + "'");
            itens.Items.Clear();
            detalhes.Visibility = System.Windows.Visibility.Hidden;
            foreach (DataRow row in campoNome.Rows)
            {
                ListBoxItem tb = new ListBoxItem();
                tb.Content = FormataString.Formata(row.ItemArray[0].ToString());
                itens.Items.Add(tb);
                listaItens.Add(row.ItemArray[0].ToString());
            }
        }

        private void limpaCampos()
        {
            Funcionando.IsChecked = false;
            Conservado.IsChecked = false;
            PerfeitoEstado.IsChecked = false;
            PequenosEstragos.IsChecked = false;
            Defeito.IsChecked = false;
            Sujo.IsChecked = false;
            Limpo.IsChecked = false;
            observacoes.Text = "";
            img1.Source = null;
            img2.Source = null;
        }

        private void carregaProriedades(string ComodoNome, string PropriedadeNome)
        {
            this.detalhes.Visibility = System.Windows.Visibility.Visible;
            db = new SQLiteDatabase(url2);
            DataTable propriedade = db.GetDataTable("SELECT * FROM Campos WHERE ImovelEnd IS '" + endereco2 + "' AND ComodoCampo IS '" + ComodoNome + "' AND Campo IS '" + PropriedadeNome +"'");
            limpaCampos();
            string detalhes = propriedade.Rows[0][4].ToString();
            string observacao = propriedade.Rows[0][5].ToString();
            try
            {
                img1.Source = new BitmapImage(new Uri(propriedade.Rows[0][6].ToString()));
                img2.Source = new BitmapImage(new Uri(propriedade.Rows[0][7].ToString()));
            }
            catch(Exception exption)
            {

            }
            if (detalhes.Contains("Funcionando"))
                Funcionando.IsChecked = true;
            if (detalhes.Contains("Conservado"))
                Conservado.IsChecked = true;
            if (detalhes.Contains("Em perfeito estado"))
                PerfeitoEstado.IsChecked = true;
            if (detalhes.Contains("Com pequenos estragos"))
                PequenosEstragos.IsChecked = true;
            if (detalhes.Contains("Com defeito"))
                Defeito.IsChecked = true;
            if (detalhes.Contains("Sujo"))
                Sujo.IsChecked = true;
            if (detalhes.Contains("Limpo"))
                Limpo.IsChecked = true;
            observacoes.Text = observacao;
        }

        private void TabControl_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            string a = listaComodos[Tabulacao.SelectedIndex];
            carregaItens(a);
        }

        private void itens_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            try
            {
                string a = listaComodos[Tabulacao.SelectedIndex];
                string b = listaItens[itens.SelectedIndex];
                carregaProriedades(a, b);
            }
            catch (Exception exp)
            {
                MessageBox.Show("falha. ERROR CODE:" + e.ToString());
            }
        }
    }
}
