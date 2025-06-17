import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class DropboxClass {

    // === Configuration ===
    private static final String ACCESS_TOKEN = "sl.u.AFwLwInNFknxGop4EltGOKvmt64zaHVt2SUmYShBOvPh_h99g69pm8tP4j4bPj9b4EhbUw78HCzQKKw7AR1Z7JX8xMpOME-7S30QTWe3FnGUZ-KAoFPH3t7l0KllSMRJfVPh6P6nxY1o9Dw80ePnfEpLbmnIYNTJPrKtfVf1qYK5toAFh6KFUZCZzEpeS-OrcGKNf5c8-daF7C2OEPbSeEjn20l1tR9E9hNCZfQvJZBzTKuCOMnPJRORE1L6pTlx3J1FsI7YNyLPbHaAljNzlEHqhzhk47DiF8qFfV53WSPARlCuhQ4kAyeIeUjuoXjo5tY9fV4Dw9ptDoynrxNSg6rghe05aJG5sAQhR9jeQpNgj42At1iB12w-tr9TLEJ2vBRs2WndNPWmpFocJIpcpaPt8-GeqMwxJAOBFL8BlcE7Q-ibnETG0dpHZ3IRaZsgzPqU8N_JbbLeXBXiuIzW0l6yFL-NdrjJHM-qlTxhSEbyOiQnb9rjcgVCZR5zXqfs2o7tIl0809Q09oxYTXO212tXuCCsgDM1dabWMRI9FmvploCiWWPgCyXyhA5h0WRRog_byeMcx29pWdzJ246-2pun7KVhcB22MGEi3-_PezPrtMavgqZQ97JCVM4WNdbmOCr17f2Xj9bhPcT6M9wFEwzPIXEOwYqkB6C3xtQzO2C4qEB3DBZQfbw8N7kgzJuvzx0WEJTHUOqvGmOnketcDmwNbSX4b7WEuKlx8Xv_Hw4t40Wf5qCLHwsnX-r22JhDxy3QRmUYz8c8BkUsNX-BaGZUtggpG9ONYMtgJ7oUzhn1RuT8uHFMf6HX8XGkbjXVN2eoY5vN1il_oIeDUrR85aSogR_KPqG9pDDFCgBMCwhO_7CUt4AvLZWNNHxaNUkS8RMqOYjQifAnSiP80DTbIZKjvEZCeibt8LU3wmIYDx_vNAlKSGvNaALJnbl96C_1Gh1SIPT7u77gKiJt1vI3p2ShKElwo9GaG293Mr6gVmyJ7piDXr4JFj0UMvIgflR5f4yThZ9q9II4mHbBTdvBg5cpBnvQTSeAOkhHANmmUNgcJYt1zHNBGIs9jke6xGTeAWZ5xkFcUgTwInhdDCIrtk1YinLQRSBi_w9E9AaazyS9XGmUCGPh3Bm--UNP0__kRTyBmmc9Cw_1siwiCdcAcidtJkDV-cAaJIuchl5NaqryqWkb_9S5vhULVBxmq_KXoxfZK3XhC-ACi-PazxSs4W2QeCDonv0rhFD5j4UU6qCzAyhWE9C3fc0g4XlpeOK2bU4"; // Replace with your Dropbox token
    private static final String LOCAL_FILE_PATH = "src/cat.txt"; // Replace with your file
    private static final String CYRILLIC_FILENAME = "clientDocument.txt";
    private static final String DROPBOX_FOLDER_PATH = "/DFD Group/Поръчки/5020 Client 13062025";

    public static void main(String[] args) {
        try {
            // Initialize Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-folder-automation")
                    .withUserLocaleFrom(Locale.getDefault())
                    .build();
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

            // Full path to upload
            String dropboxPath = DROPBOX_FOLDER_PATH + "/" + CYRILLIC_FILENAME;

            // Upload file
            try (InputStream in = new FileInputStream(LOCAL_FILE_PATH)) {
                FileMetadata metadata = client.files().uploadBuilder(dropboxPath)
                        .withMode(WriteMode.OVERWRITE)
                        .uploadAndFinish(in);

                System.out.println("✅ File uploaded to Dropbox: " + metadata.getPathDisplay());
            }

        } catch (Exception ex) {
            System.err.println("Error uploading file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
