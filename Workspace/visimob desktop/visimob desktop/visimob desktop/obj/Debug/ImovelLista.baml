   M S B A M L   `   `   `  ����  visimob desktop!    visimob_desktop.ImovelLista   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	     � Title$  ImovelLista��5   	   $	��500��6   $
��1000��6*   .��5      ]� .+�5   
   `� $	��Top=�6   .�5      c� .@�5      �� -   5        $��	Atualizar��5      6-   �� 5	      -   5        $��Cancelar��5	      6,   �� 5
      -   5        $��Editar Imóvel��5
      $��Bottom=�6F   $��	0,0,0,0.4q�6a   �� 5      $��Gerar Vistoria��6   �� 5      $��Ajuda��6   5      5      &   System.Windows.Controls.DataGrid  5   
   -   5        $��tabelaImoveis��5   �   $��20(�6E      	RowHeight$ 40Q�6S      ColumnHeaderHeight$ 40Q�6b      
IsReadOnly .    CanUserAddRows .     CanUserDeleteRows .     AlternatingRowBackground �#e5e5e5   AutoGenerateColumns .     Columns 5      0  *System.Windows.Controls.DataGridTextColumn  5      ,  &System.Windows.Controls.DataGridColumn	   Header$	 Locador��6A   1  +System.Windows.Controls.DataGridBoundColumn
   Binding
 6%   �� *	Locador+6T     5      $	 
Locatário��6C   
 6%   �� *	Locatario+6X     5      $	 	Endereço��6B   
 6%   �� *
Endereco+6V     5      $	 Bairro��6@   
 6%   �� *Bairro+6R     5      $
	 Tipo��6>   
 6%   �� *Tipo+6N     5      $
	 Data��6>   
 6%   �� *Data+6N   5      5      5      