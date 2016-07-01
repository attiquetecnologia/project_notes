/*
 * Copyright (C) 2015 Rodrigo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package views;

import java.awt.Frame;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import models.Nota;
import persistence.NotaDAO;

/**
 *
 * @author Rodrigo
 * @version 1.0
 * 27/04/2015
 */
public class PesquisaNotas extends FrmPesquisaPadrao implements models.DefaultTableResult {

    private Object[] colName;
    private final int[] colSize;
    private Object[][] rows;
    private ArrayList<Nota> notas;
    private Nota nota;
    
    @SuppressWarnings("empty-statement")
    public PesquisaNotas(Frame parent, boolean modal) {
        super(parent, modal);
        setLocationRelativeTo(this);
        this.colName = new Object[]{"ID", "TITULO", "DATA", "PROJETO"};
        this.colSize = new int[]{50,320,100,150};
        setCmbFiltro(colName);
        setIconImage(new ImageIcon("./icone.png").getImage());
        buscar();
    }
    
    public Nota getNota(){
        selecionar();
        return nota;
    }
    private void buscar(){
        search();
    }
    
    @Override
    public void fechar(){
        this.dispose();
    }
    
    @Override
    public void novo(){
        FrmNota fn = new FrmNota(null, rootPaneCheckingEnabled);
        fn.setDataForm(new Nota(0L, null, LocalDateTime.now(), null, null));
        fn.setVisible(rootPaneCheckingEnabled);
        search();
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void search(){
        NotaDAO ndao = new NotaDAO();
        String query = "select * from notas n "
                + "where ("
                + "upper(titulo) like upper(?) "
                + "or "
                + "upper(projeto) like upper(?) "
                //+ "or "
                //+ "data_ocorr like ? "
                + "or "
                + "upper(texto) like upper(?)) "
                + "order by n.titulo";
        
        //System.out.println(query);
        try {
            notas = (ArrayList<Nota>) ndao.findByParameters(query
                    , "%"+getPesquisa()+"%" //titulo
                    , "%"+getPesquisa()+"%" //projeto
                    //, "%"+java.sql.Timestamp.valueOf(getPesquisa())+"%" //data
                    , "%"+getPesquisa()+"%" //TEXTO
            ); //texto
            
            if (!notas.isEmpty()) {
                rows = new Object[notas.size()][colName.length];
                for (int i = 0; i < notas.size(); i++) {
                    rows[i][0] = notas.get(i).getId();
                    rows[i][1] = notas.get(i).getTitulo();
                    rows[i][2] = notas.get(i).getDataOcorrencia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    rows[i][3] = notas.get(i).getProjeto();
                }//fim for
                
                setDataTable(rows, colName, colSize);
            }//fim if
        } catch (SQLException ex) {
            br.attique.utils.logs.Log.log(getName(), ex);
        }
    }//fim search()
    
    @Override
    public void selecionar(){
        nota = new Nota();
        //colName = getDataRow();
        nota.setId((Long) getDataRow()[0]);
        NotaDAO ndao = new NotaDAO();
        try {
            if(!ndao.findById(this.nota)){
                JOptionPane.showMessageDialog(this, "Nenhum valor encontrado!");
            }
            dispose();
        } catch (SQLException ex) {
            br.attique.utils.logs.Log.log(getName(), ex);
        }
    }//fim selecionar
    
    @Override
    public void editar(){
        NotaDAO ndao = new NotaDAO();
        nota = new Nota();
        colName = getDataRow();
        nota.setId((Long) colName[0]);
        try {
            if (ndao.findById(nota)){
                FrmNota fn = new FrmNota(null, rootPaneCheckingEnabled);
                fn.setDataForm(nota);
                fn.setVisible(rootPaneCheckingEnabled);
                search();
            }
        } catch (SQLException ex) {
            br.attique.utils.logs.Log.log(getName(), ex);
        }
    }//fim editar
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PesquisaNotas dialog = new PesquisaNotas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
}//fim classe