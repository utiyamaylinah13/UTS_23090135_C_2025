import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import java.util.Scanner;

public class CRUD_23090135_C_2025 {
   // Setup koneksi MongoDB
    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    static MongoDatabase database = mongoClient.getDatabase("uts_23090135_C_2025");
    static MongoCollection<Document> collection = database.getCollection("cool_23090135_C_2025");

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n=== APLIKASI CRUD MONGODB - UTS ===");
            System.out.println("1. Create Data");
            System.out.println("2. Read Data");
            System.out.println("3. Update Data");
            System.out.println("4. Delete Data");
            System.out.println("5. Search Data");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();
            input.nextLine(); // buang newline

            switch (pilihan) {
                case 1 -> createData();
                case 2 -> readData();
                case 3 -> updateData();
                case 4 -> deleteData();
                case 5 -> searchData();
            }
        } while (pilihan != 0);

        mongoClient.close();
    }

    // Create: Tambah 3 data dengan dimensi berbeda
    public static void createData() {
        for (int i = 1; i <= 3; i++) {
            System.out.println("Masukkan data ke-" + i);
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
            System.out.println("Data ke-" + i + " berhasil disimpan.\n");
        }
    }

    // Read: Menampilkan semua data
    public static void readData() {
        System.out.println("\n=== DATA YANG TERSIMPAN ===");
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }

    // Update: Mengubah salah satu value berdasarkan nim
    public static void updateData() {
        System.out.print("Masukkan NIM yang ingin diubah: ");
        String nim = input.nextLine();

        System.out.print("Masukkan nama baru: ");
        String namaBaru = input.nextLine();

        collection.updateOne(Filters.eq("nim", nim), new Document("$set", new Document("nama", namaBaru)));
        System.out.println("Data berhasil diupdate.");
    }

    // Delete: Menghapus berdasarkan nim
    public static void deleteData() {
        System.out.print("Masukkan NIM yang ingin dihapus: ");
        String nim = input.nextLine();
        collection.deleteOne(Filters.eq("nim", nim));
        System.out.println("Data berhasil dihapus.");
    }

    // Search: Mencari berdasarkan nama
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

    

