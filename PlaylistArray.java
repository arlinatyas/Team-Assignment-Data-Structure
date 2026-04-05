/**
 * Tugas Kelompok Week 4: Arrays and its Operations dan OOP Playlist
 * Group 6:
 * 1. Imam Sasmita Purna Sakti (2902739684)
 * 2. Luthfiana Adaam Maldini (2902724045)
 * 3. Mahesa Rangga Guntara (2902739715)
 * 4. Arlina Yunantining Tyas (2902738290)
 * 5. Rini S. Marpaung (2902700713)
 * Learning Outcome: LO2 - Analyze algorithm complexity
 */
package W4;
import java.util.Scanner;

//Class Lagu untuk merepresentasikan data lagu
class Lagu {
    private String judul;
    private String artis;
    private double durasi;  //dalam detik

    //Constructor
    public Lagu(String judul, String artis, double durasi){
        this.judul = judul;
        this.artis = artis;
        this.durasi = durasi;
    }

    //Getters
    public String getJudul() { return judul; }
    public String getArtis() { return artis; }
    public double getDurasi() { return durasi; }

    //Setters
    public void setJudul(String judul) { this.judul = judul; }
    public void setArtis(String artis) { this.artis = artis; }
    public void setDurasi(double durasi) { this.durasi = durasi; }

    //Tampilkan info lagu dengan format menit.detik (Contoh: 4.23)
    public void tampilkanInfo() {
        System.out.printf("%s - %s (%.2f menit)\n", artis, judul, durasi);
    }

    //Override toString untuk format output tabel
    @Override
    public String toString() {
        return String.format("%s - %s (%.2f menit)", artis, judul, durasi);
    }
}

//Class utama PlaylistArray
public class PlaylistArray {
    private Lagu[] playlist;    //Array statis maks 10 lagu
    private int jumlahLagu;     //Jumlah lagu saat ini

    //Constructor: O(1) - inisialisasi array dan 3 lagu awal
    public PlaylistArray() {
        this.playlist = new Lagu[10];   //Maksimal 10 lagu
        this.jumlahLagu = 0;

        //Inisialisasi 3 lagu awal (format menit)
        tambahLaguAwal("Perfect", "Ed Sheeran", 4.23);
        tambahLaguAwal("Shivers", "Ed Sheeran", 3.50);
        tambahLaguAwal("Yellow", "Coldplay", 4.10);
        
        System.out.println("Playlist diinisialisasi dengan 3 lagu awal!");
    }

    //Helper method untuk lagu awal: O(1)
    private void tambahLaguAwal(String judul, String artis, double durasi) {
        Lagu laguBaru = new Lagu(judul, artis, durasi);
        playlist[jumlahLagu] = laguBaru;
        jumlahLagu++;
    }

    //1. TRAVERSAL: O(n) - menelusuri semua elemen array
    public void tampilkanSemuaLagu() {
        System.out.println("\n=== DAFTAR LAGU (" + jumlahLagu + "/10) ===");
        if (jumlahLagu == 0) {
            System.out.println("Playlist kosong!");
        } else {
            for (int i = 0; i < jumlahLagu; i++) {
                System.out.printf("%d. %s\n", (i + 1), playlist[i]);
            }
        }
        System.out.println();
    }

    //2. INSERTION: O(1) amortized, O(n) worst case jika penuh
    public void tambahLagu() {
        if (jumlahLagu >= 10) {
            System.out.println("Playlist penuh! Maksimal 10 lagu!");
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan judul lagu: ");
        String judul = input.nextLine();
        System.out.print("Masukkan artis: ");
        String artis = input.nextLine();
        System.out.print("Masukkan durasi (menit): ");
        double durasi = input.nextDouble();

        //Insertion di akhir array: O(1)
        Lagu laguBaru = new Lagu(judul, artis, durasi);
        playlist[jumlahLagu] = laguBaru;
        jumlahLagu++;
        System.out.println("Lagu berhasil ditambahkan! Total: " + jumlahLagu + "/10");
    }

    //3. SEARCHING (Linear Search): O(n) - worst case harus cek semua elemen
    public void cariLagu() {
        Scanner input = new Scanner(System.in);
        System.out.print("Cari lagu berdasarkan judul: ");
        String judulCari = input.nextLine().toLowerCase();

        boolean ditemukan = false;
        //Linear search: membandingkan setiap elemen
        for (int i = 0; i < jumlahLagu; i++) {
            if (playlist[i].getJudul().toLowerCase().contains(judulCari)) {
                System.out.println("\nDitemukan pada posisi " + (i + 1) + ":");
                playlist[i].tampilkanInfo();
                ditemukan = true;
                break; //Early termination: O(k) dimana k <= n
            }
        }
        if (!ditemukan) {
            System.out.println("Lagu tidak ditemukan!");
        }
    }

    //4. DELETION: O(n) = O(n) untuk search + O(n) untuk shift
    public void hapusLagu() {
        Scanner input = new Scanner(System.in);
        System.out.print("Hapus lagu berdasarkan judul: ");
        String judulHapus = input.nextLine().toLowerCase();

        //Step 1: Linear search untuk menemukan index: O(n)
        int indexHapus = -1;
        for (int i = 0; i < jumlahLagu; i++) {
            if (playlist[i].getJudul().toLowerCase().contains(judulHapus)) {
                indexHapus = i;
                break;
            }
        }

        if (indexHapus == -1) {
            System.out.println("Lagu tidak ditemukan!");
            return;
        }

        //Konfirmasi hapus
        System.out.printf("Hapus '%s'? (Y/N): ", playlist[indexHapus].getJudul());
        Scanner sc = new Scanner(System.in);
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            //Step 2: Shift elemen ke kiri: O(n)
            for (int i = indexHapus; i < jumlahLagu - 1; i++) {
                playlist[i] = playlist[i + 1];
            }
            jumlahLagu--;
            playlist[jumlahLagu] = null; //Clear last position
            System.out.println("Lagu dihapus! Total: " + jumlahLagu + "/10");
        } else {
            System.out.println("Dibatalkan!");
        }
    }

    //5. SORTING (Bubble Sort): O(n²) - nested loop
    public void urutkanLaguBerdasarkanDurasi() {
        if (jumlahLagu <= 1) {
            System.out.println("Minimal 2 lagu untuk sorting!");
            return;
        }

        System.out.println("Sebelum diurutkan:");
        tampilkanSemuaLagu();

        //Bubble Sort: O(n²) - 2 nested loops
        for (int i = 0; i < jumlahLagu - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < jumlahLagu - i - 1; j++) {
                //Compare durasi dan swap jika perlu
                if (playlist[j].getDurasi() > playlist[j + 1].getDurasi()) {
                    Lagu temp = playlist[j];
                    playlist[j] = playlist[j + 1];
                    playlist[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; //Optimized: early termination
        }
        
        System.out.println("Diurutkan berdasarkan durasi (ascending)!");
        tampilkanSemuaLagu();
    }

    //MENU INTERAKTIF - sesuai spesifikasi
    public void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        System.out.println("=== SELAMAT DATANG DI PLAYLIST MUSIK ===");
        tampilkanSemuaLagu();

        do {
            System.out.println("\n=== MENU PLAYLIST MUSIK ===");
            System.out.println("1. Tampilkan semua lagu");
            System.out.println("2. Tambah lagu baru");
            System.out.println("3. Hapus lagu berdasarkan judul");
            System.out.println("4. Cari lagu berdasarkan judul");
            System.out.println("5. Urutkan berdasarkan durasi");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");

            pilihan = input.nextInt();
            input.nextLine(); //Clear buffer

            switch (pilihan) {
                case 1: 
                    tampilkanSemuaLagu(); 
                    break;
                case 2: 
                    tambahLagu(); 
                    tampilkanSemuaLagu(); //Show result
                    break;
                case 3: 
                    hapusLagu(); 
                    tampilkanSemuaLagu(); //Show result
                    break;
                case 4: 
                    cariLagu(); 
                    break;
                case 5: 
                    urutkanLaguBerdasarkanDurasi(); 
                    break;
                case 6: 
                    System.out.println("Terima kasih telah menggunakan PlaylistArray!");
                    break;
                default: 
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 6);
    }

    //Main method
    public static void main(String[] args) {
        PlaylistArray playlist = new PlaylistArray();
        playlist.menu();
    }
}
