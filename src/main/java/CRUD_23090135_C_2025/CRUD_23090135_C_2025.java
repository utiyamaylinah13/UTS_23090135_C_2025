package CRUD_23090135_C_2025;

import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;

import java.util.Scanner;

public class CRUD_23090135_C_2025 {

    // Setup koneksi MongoDB
    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    static MongoDatabase database = mongoClient.getDatabase("uts_23090135_C_2025");
    static MongoCollection<Document> collection = database.getCollection("coll_23090135_C_2025");

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n=== CRUD Manajemen Data Mahasiswa ===");
            System.out.println("1. Create Data");
            System.out.println("2. Read Data");
            System.out.println("3. Update Data");
            System.out.println("4. Delete Data");
            System.out.println("5. Search Data");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();
            input.nextLine(); // membersihkan newline

            switch (pilihan) {
                case 1 -> createData();
                case 2 -> readData();
                case 3 -> updateData();
                case 4 -> deleteData();
                case 5 -> searchData();
                case 0 -> System.out.println("Terima kasih!");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);

        mongoClient.close();
    }

    // CREATE: Menambahkan 3 data mahasiswa
    public static void createData() {
        for (int i = 1; i <= 3; i++) {
            System.out.println("\nMasukkan data ke-" + i);
            System.out.print("NIM     : ");
            String nim = input.nextLine();
            System.out.print("Nama    : ");
            String nama = input.nextLine();
            System.out.print("Kelas   : ");
            String kelas = input.nextLine();
            System.out.print("Tahun   : ");
            String tahun = input.nextLine();

            Document doc = new Document("nim", nim)
                    .append("nama", nama)
                    .append("kelas", kelas)
                    .append("tahun", tahun);

            collection.insertOne(doc);
            System.out.println("Data ke-" + i + " berhasil disimpan.");
        }
    }

    // READ: Menampilkan semua data
    public static void readData() {
        System.out.println("\n=== Data Mahasiswa ===");
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }

    // UPDATE: Mengubah seluruh data berdasarkan NIM lama
    public static void updateData() {
        System.out.print("Masukkan NIM yang ingin diubah: ");
        String nimLama = input.nextLine().trim();

        System.out.println("Masukkan data baru:");
        System.out.print("NIM Baru     : ");
        String nimBaru = input.nextLine();
        System.out.print("Nama Baru    : ");
        String namaBaru = input.nextLine();
        System.out.print("Kelas Baru   : ");
        String kelasBaru = input.nextLine();
        System.out.print("Tahun Baru   : ");
        String tahunBaru = input.nextLine();

        Document dataBaru = new Document("nim", nimBaru)
                .append("nama", namaBaru)
                .append("kelas", kelasBaru)
                .append("tahun", tahunBaru);

        UpdateResult result = collection.updateOne(Filters.eq("nim", nimLama), new Document("$set", dataBaru));

        if (result.getModifiedCount() > 0) {
            System.out.println("Data berhasil diupdate.");
        } else {
            System.out.println("Data tidak ditemukan atau tidak diubah.");
        }
    }

    // DELETE: Menghapus data berdasarkan NIM
    public static void deleteData() {
        System.out.print("Masukkan NIM mahasiswa yang akan dihapus: ");
        String nim = input.nextLine();

        DeleteResult result = collection.deleteOne(Filters.eq("nim", nim));

        if (result.getDeletedCount() > 0) {
            System.out.println("Data berhasil dihapus.");
        } else {
            System.out.println("Data tidak ditemukan.");
        }
    }

    // SEARCH: Mencari data berdasarkan nama (dengan regex)
    public static void searchData() {
        System.out.print("Masukkan nama yang dicari: ");
        String keyword = input.nextLine();

        FindIterable<Document> docs = collection.find(Filters.regex("nama", ".*" + keyword + ".*", "i"));

        System.out.println("Hasil pencarian:");
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }
}
