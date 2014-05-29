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
    /// Interaction logic for EditarImovel.xaml
    /// </summary>
    public partial class EditarImovel : Window
    {
        SQLiteDatabase db;
        string url2, indx;
        DataTable info;
        public EditarImovel(int indice, string url)
        {
            db = new SQLiteDatabase(url);
            url2 = url;
            info = db.GetDataTable("SELECT * FROM Imoveis");
            InitializeComponent();
            DataRow row = info.Rows[indice];
            indx = row.ItemArray[0].ToString();
            locador.Text = row.ItemArray[1].ToString();
            locatario.Text = row.ItemArray[2].ToString();
            endereco.Text = row.ItemArray[3].ToString();
            bairro.Text = row.ItemArray[4].ToString();
            tipo.Text = row.ItemArray[5].ToString();
            data.Text = row.ItemArray[6].ToString();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            //salvar
            db = new SQLiteDatabase(url2);
            Dictionary<string, string> dataf = new Dictionary<string, string>();
            dataf.Add("locador", locador.Text);
            dataf.Add("locatario", locatario.Text);
            dataf.Add("endereco", endereco.Text);
            dataf.Add("bairro", bairro.Text);
            dataf.Add("tipo", tipo.Text);
            dataf.Add("data", data.Text);
            try
            {
                db.Update("Imoveis", dataf, String.Format("ImovelID = {0}", indx));
            }
            catch(Exception exp)
            {
                MessageBox.Show("erro " + exp.ToString());
            }
            MessageBox.Show("Salvo!");
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            Imovel edt = new Imovel(url2,indx);
            edt.Show();
        }
    }
}
