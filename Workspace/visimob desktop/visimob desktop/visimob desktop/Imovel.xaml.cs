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
        string url2, index;
        DataTable info;
        public Imovel(string url, string index)
        {
            db = new SQLiteDatabase(url);
            url2 = url;
            info = db.GetDataTable("SELECT * FROM Imoveis");
            InitializeComponent();
            this.WindowState = WindowState.Maximized;
        }

        private void TabControl_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }
    }
}
