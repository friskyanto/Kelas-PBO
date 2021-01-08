 package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.*;
import java.sql.ResultSet;

public class Controller {


    public TableColumn<TampilTabel, SimpleStringProperty>colId; // mendeklarasikan untuk tabel view
    public TableColumn<TampilTabel, SimpleStringProperty>colNama;
    public TableColumn<TampilTabel, SimpleStringProperty>colPlat;
    public TableColumn<TampilTabel, SimpleStringProperty>colJk;
    public TableView<TampilTabel>tbData;
    private Connection conn;
    public TextField txtfldID;
    public TextField txtfldNama;
    public TextField txtfldPlat;
    public TextField txtJenis;

    public Button btnDelete;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnShow;
    public Button btnClear;

    private Koneksi Koneksi = new Koneksi(); // menyabungkan ke koneksi

    public void setOcClickadd(ActionEvent actionEvent) { // agar bisa di add

        String id = txtfldID.getText(); //  untuk bisa memasukan id
        String nama = txtfldNama.getText();// untuk bisa memasukan nama
        String plat = txtfldPlat.getText();// memasukan plat
        String kendaraan = txtJenis.getText();// memasukan jenias kenadaraan

        txtfldID.setText("");//biar muncul di scane builder
        txtfldNama.setText("");// agar ,umcul di scane builder
        txtfldPlat.setText("");// agar muncul di scane builder
        txtJenis.setText("");// agar muncul scane builder

        //input data ke tabel pesan pada database pbo-reguler
        String query ="INSERT INTO steam(id, nama, plat, kendaraan) VALUES(" + id + ", '" + nama + "', '" + plat + "', '" + kendaraan +"')";
        int hasil = Koneksi.manipulasiData(query);

        if (hasil == 1) {// jika hasil truee maka akan muncul data bersahil di update
            System.out.println("Data berhasil ditambah ke dalam database");
            this.tampilDataTabelView();
        }
    }

    public void setOnClickupdate(ActionEvent actionEvent) {// saat di klik bisa terupdate

        String id = txtfldID.getText();  // agar bisa du update
        String nama = txtfldNama.getText();
        String plat = txtfldPlat.getText();
        String kendaraan = txtJenis.getText();

        String query = "UPDATE steam set nama = '"+ nama +"', plat = '"+ plat +"', kendaraan = '"+ kendaraan+" ' WHERE id = '" + id + "'";
        int hasil = Koneksi.manipulasiData(query);// update data di steam yang ada di database , jika hasil true makan akan muncul data bershail di update

        if (hasil == 1) {
            System.out.println("Data berhasil di update");
            this.tampilDataTabelView();// jika truee maka akan berhasil di update
        }
    }

    public void setOnClickdelete(ActionEvent actionEvent) { // agar pada scane builder dapat ter hapus/delete
        // data yang bisa di delete
        String id = txtfldID.getText();
        String nama = txtfldNama.getText();
        String plat = txtfldPlat.getText();
        String kendaraan = txtJenis.getText();

        String query = "DELETE FROM steam WHERE id = '" + id + "'";
        int hasil = Koneksi.manipulasiData(query);
        // jika turee maka data ber hasil di hapus dari database
        if (hasil == 1) {
            System.out.println("Data berhasil dihapus dari database");
            //data yang bisa di update
            txtfldID.setText("");
            txtfldNama.setText("");
            txtfldPlat.setText("");
            txtJenis.setText("");
            this.tampilDataTabelView();
        }
    }

    public void setOnClickshow(ActionEvent actionEvent) { // untuk menaptilkan data pada tabel dari database
        // Lokasi database
        try {
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/pbo-reguler";
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT id, nama, plat, kendaraan FROM steam WHERE id = '" +txtfldID.getText()+ "'");

            while (rs.next()) { // untuk menampilkan berdasarkan id,jika id ttuee maka data yang lain akan masuk
                String id = rs.getString("id");
                String nama = rs.getString("nama");
                String plat = rs.getString("plat");
                String kendaraan = rs.getString("kendaraan");
                txtfldNama.setText(nama);
                txtfldPlat.setText(plat);
                txtJenis.setText(kendaraan);
            }
        } catch (SQLException e) {// untuk menaplikan error
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void setOnClickclear(ActionEvent actionEvent) { //untuk menghapus data pada scane builder
        // data yang akan terhapus
        txtfldID.setText("");
        txtfldNama.setText("");
        txtfldPlat.setText("");
        txtJenis.setText("");
        this.tampilDataTabelView();
    }
    private void tampilDataTabelView() { // untuk menampilkan data pada tabel view
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        colPlat.setCellValueFactory(new PropertyValueFactory<>("Plat"));
        colJk.setCellValueFactory(new PropertyValueFactory<>("Kendaraan"));

        try {// tabel bersadarkan database
            String query = "SELECT * FROM steam";
            ResultSet hasil = Koneksi.getData(query);
            ObservableList<TampilTabel> TampilTabels = FXCollections.observableArrayList();
            tbData.setItems(TampilTabels);
            while (hasil.next()) {
                // Urutan yang akan keluar pada tabel
                String id = hasil.getString(1);
                String nama = hasil.getString(2);
                String plat = hasil.getString(3);
                String kendaraan = hasil.getString(4);
                TampilTabels.add(new TampilTabel(id,nama,plat,kendaraan));
            }
        }catch (Exception e) { // untuk menampilkan error
            System.out.println(e);
        }
        tbData.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPesanDetail(newValue));
    }
    private void showPesanDetail(TampilTabel pesan) {// pesan yang akan di tampilkan pada tabel data
        if (pesan != null) {
            txtfldID.setText(pesan.getId());
            txtfldNama.setText(pesan.getNama());
            txtfldPlat.setText(pesan.getPlat());
            txtJenis.setText(pesan.getKendaraan());
        } else {
            txtfldID.setText("");
            txtfldNama.setText("");
            txtfldPlat.setText("");
            txtJenis.setText("");

        }
    }

}