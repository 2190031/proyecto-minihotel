/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import Connection.myConnection;
import clases.c_usuario;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import modelo.md_hash;
import modelo.md_usuario;

/**
 *
 * @author maxim
 */
public class frm_login extends javax.swing.JFrame {


    
    public frm_login() {
        initComponents();
//        setExtendedState(getExtendedState() | frm_login.MAXIMIZED_BOTH);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        label2 = new java.awt.Label();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbl_login = new javax.swing.JLabel();
        img_pswd = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        btn_enter = new javax.swing.JButton();
        txt_contraseña = new javax.swing.JPasswordField();
        img_logo = new javax.swing.JLabel();
        img_user = new javax.swing.JLabel();
        tglbtn_mostrar = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();

        jLabel1.setText("jLabel1");

        label2.setText("label2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iniciar sesión");
        setMaximumSize(new java.awt.Dimension(32767, 32767));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        jPanel2.setBackground(new java.awt.Color(219, 35, 35));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_login.setFont(new java.awt.Font("Corbel", 0, 48)); // NOI18N
        lbl_login.setForeground(new java.awt.Color(148, 148, 148));
        lbl_login.setText("Iniciar sesión");

        img_pswd.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        img_pswd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/secure.png"))); // NOI18N

        txt_email.setFont(new java.awt.Font("Dubai Light", 0, 19)); // NOI18N
        txt_email.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emailKeyPressed(evt);
            }
        });

        btn_enter.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        btn_enter.setForeground(new java.awt.Color(51, 51, 51));
        btn_enter.setText("Iniciar sesión");
        btn_enter.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_enter.setFocusPainted(false);
        btn_enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enterActionPerformed(evt);
            }
        });

        txt_contraseña.setFont(new java.awt.Font("Dubai Light", 0, 19)); // NOI18N
        txt_contraseña.setPreferredSize(new java.awt.Dimension(4, 37));
        txt_contraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_contraseñaKeyPressed(evt);
            }
        });

        img_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        img_user.setForeground(new java.awt.Color(255, 153, 153));
        img_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N

        tglbtn_mostrar.setForeground(new java.awt.Color(255, 255, 255));
        tglbtn_mostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/visibility1.png"))); // NOI18N
        tglbtn_mostrar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tglbtn_mostrar.setBorderPainted(false);
        tglbtn_mostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tglbtn_mostrar.setFocusPainted(false);
        tglbtn_mostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tglbtn_mostrarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tglbtn_mostrarMouseReleased(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 51, 51));

        jSeparator2.setForeground(new java.awt.Color(255, 51, 51));

        jSeparator3.setForeground(new java.awt.Color(255, 51, 51));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(307, 307, 307)
                        .add(img_logo))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(237, 237, 237)
                        .add(lbl_login))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(107, 107, 107)
                        .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 490, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(117, 117, 117)
                        .add(img_user)
                        .add(16, 16, 16)
                        .add(txt_email, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 340, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(107, 107, 107)
                        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 490, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(117, 117, 117)
                        .add(img_pswd)
                        .add(16, 16, 16)
                        .add(txt_contraseña, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 285, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tglbtn_mostrar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(107, 107, 107)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(258, 258, 258)
                        .add(btn_enter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(116, 116, 116))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(37, 37, 37)
                .add(img_logo)
                .add(20, 20, 20)
                .add(lbl_login, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(img_user)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(txt_email, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(16, 16, 16)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(img_pswd)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(tglbtn_mostrar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(txt_contraseña, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(16, 16, 16)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btn_enter)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(40, 40, 40)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_enterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enterActionPerformed
        md_usuario modUs = new md_usuario();
        Date date = new Date();
        DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String email = txt_email.getText();
        String cla_txt = new String(txt_contraseña.getPassword());
        if (email.equals("") || cla_txt.equals("")) {

            JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío");

        } else {
            if (modUs.esEmail(email)) {
                PreparedStatement ps;
                ResultSet rs;
                String query_adm = "SELECT * FROM `usuario` WHERE `correo_electronico` =? AND `clave` =? AND `id_rol` = 1";
                String cla = md_hash.md5(cla_txt);
                try {
                    ps = myConnection.getConnection().prepareStatement(query_adm);

                    ps.setString(1, email);
                    ps.setString(2, cla);

                    rs = ps.executeQuery();
                    if (rs.next()) {
                        
                        frm_menu mf = new frm_menu();
                        mf.setVisible(true);
                        mf.pack();
                        mf.setLocationRelativeTo(null);
                        frm_menu.txt_nom_emp_fact.setText(rs.getString(2));
                         frm_menu.id_empleado = rs.getInt(1);
                        this.dispose();

                    } else {

                        c_usuario C_usu = new c_usuario();
                        

                        C_usu.setCorreo_e(email);
                        C_usu.setClave_e(cla);
                        C_usu.setLast_session(fechaHora.format(date));
                        
                        if(modUs.login(C_usu)){
                       
                              frm_menu nf = new frm_menu(C_usu);
                              nf.setVisible(true);
                              nf.setLocationRelativeTo(null);
                              
                              frm_menu.txt_nom_emp_fact.setText(C_usu.getNombre_e() + " " + C_usu.getApellido_e());
                              frm_menu.id_empleado = C_usu.getId_e();
                              this.dispose();
                            
                        }else{
                              JOptionPane.showMessageDialog(null, "Error. los datos ingresados no son correctos.");
                        }
                        
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(frm_login.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error " + ex);
                }
            } else {
                         JOptionPane.showMessageDialog(null, "Error, el correo debe de tener un dominio válido.");
            }
        }


    }//GEN-LAST:event_btn_enterActionPerformed

    private void tglbtn_mostrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tglbtn_mostrarMousePressed
         txt_contraseña.setEchoChar((char)0);
         tglbtn_mostrar.setIcon(new ImageIcon(getClass().getResource("/img/visibility2.png")));
    }//GEN-LAST:event_tglbtn_mostrarMousePressed

    private void tglbtn_mostrarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tglbtn_mostrarMouseReleased
         txt_contraseña.setEchoChar('*');
         tglbtn_mostrar.setIcon(new ImageIcon(getClass().getResource("/img/visibility1.png")));
    }//GEN-LAST:event_tglbtn_mostrarMouseReleased

    private void txt_emailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            md_usuario modUs = new md_usuario();
            Date date = new Date();
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String email = txt_email.getText();
            String cla_txt = new String(txt_contraseña.getPassword());
            if (email.equals("") || cla_txt.equals("")) {

                JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío");

            } else {
                if (modUs.esEmail(email)) {
                    PreparedStatement ps;
                    ResultSet rs;
                    String query_adm = "SELECT * FROM `usuario` WHERE `correo_electronico` =? AND `clave` =? AND `id_rol` = 1";
                    String cla = md_hash.md5(cla_txt);
                    try {
                        ps = myConnection.getConnection().prepareStatement(query_adm);

                        ps.setString(1, email);
                        ps.setString(2, cla);

                        rs = ps.executeQuery();
                        if (rs.next()) {

                            frm_menu mf = new frm_menu();
                            mf.setVisible(true);
                            mf.pack();
                            mf.setLocationRelativeTo(null);
                            frm_menu.txt_nom_emp_fact.setText(rs.getString(2));
                             frm_menu.id_empleado = rs.getInt(1);
                            this.dispose();

                        } else {

                            c_usuario C_usu = new c_usuario();


                            C_usu.setCorreo_e(email);
                            C_usu.setClave_e(cla);
                            C_usu.setLast_session(fechaHora.format(date));

                            if(modUs.login(C_usu)){

                                  frm_menu nf = new frm_menu(C_usu);
                                  nf.setVisible(true);
                                  nf.setLocationRelativeTo(null);

                                  frm_menu.txt_nom_emp_fact.setText(C_usu.getNombre_e() + " " + C_usu.getApellido_e());
                                  frm_menu.id_empleado = C_usu.getId_e();
                                  this.dispose();

                            }else{
                                  JOptionPane.showMessageDialog(null, "Error. los datos ingresados no son correctos.");
                            }

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(frm_login.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }
                } else {
                             JOptionPane.showMessageDialog(null, "Error, el correo debe de tener un dominio válido.");
                }
            }
        }
    }//GEN-LAST:event_txt_emailKeyPressed

    private void txt_contraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contraseñaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            md_usuario modUs = new md_usuario();
            Date date = new Date();
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String email = txt_email.getText();
            String cla_txt = new String(txt_contraseña.getPassword());
            if (email.equals("") || cla_txt.equals("")) {

                JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío");

            } else {
                if (modUs.esEmail(email)) {
                    PreparedStatement ps;
                    ResultSet rs;
                    String query_adm = "SELECT * FROM `usuario` WHERE `correo_electronico` =? AND `clave` =? AND `id_rol` = 1";
                    String cla = md_hash.md5(cla_txt);
                    try {
                        ps = myConnection.getConnection().prepareStatement(query_adm);

                        ps.setString(1, email);
                        ps.setString(2, cla);

                        rs = ps.executeQuery();
                        if (rs.next()) {

                            frm_menu mf = new frm_menu();
                            mf.setVisible(true);
                            mf.pack();
                            mf.setLocationRelativeTo(null);
                            frm_menu.txt_nom_emp_fact.setText(rs.getString(2));
                             frm_menu.id_empleado = rs.getInt(1);
                            this.dispose();

                        } else {

                            c_usuario C_usu = new c_usuario();


                            C_usu.setCorreo_e(email);
                            C_usu.setClave_e(cla);
                            C_usu.setLast_session(fechaHora.format(date));

                            if(modUs.login(C_usu)){

                                  frm_menu nf = new frm_menu(C_usu);
                                  nf.setVisible(true);
                                  nf.setLocationRelativeTo(null);

                                  frm_menu.txt_nom_emp_fact.setText(C_usu.getNombre_e() + " " + C_usu.getApellido_e());
                                  frm_menu.id_empleado = C_usu.getId_e();
                                  this.dispose();

                            }else{
                                  JOptionPane.showMessageDialog(null, "Error. los datos ingresados no son correctos.");
                            }

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(frm_login.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error, el correo debe de tener un dominio válido.");
                }
            }
        }
    }//GEN-LAST:event_txt_contraseñaKeyPressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_login().setVisible(true);
                
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_enter;
    private javax.swing.JLabel img_logo;
    private javax.swing.JLabel img_pswd;
    private javax.swing.JLabel img_user;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private java.awt.Label label2;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JToggleButton tglbtn_mostrar;
    private javax.swing.JPasswordField txt_contraseña;
    private javax.swing.JTextField txt_email;
    // End of variables declaration//GEN-END:variables
}
