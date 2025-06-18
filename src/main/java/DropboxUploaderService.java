import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

public class DropboxUploaderService {
    private static final String ACCESS_TOKEN = "sl.u.AFwlKh8pMWjNamxJ9YxhvrSwl05a1xf6McEpzy07S8zjqUdFefHx7PvZ_ih5jSMkd8i2Yw72Ev4HluB5Po1hEjF6HkKsi48wqsXnsJaFxjWevOK2lsFhqrZWtwEYsNB8bJ2s5jIMzKMSzi3kvOjTjNfwgeibP1RfSxb4rnrmzBZqVT9nIEefDb5r8Yuz3HEUNuuall3RMWFN_O4RcE53VwnC3eDcJ7rIUpa24nn_ULHYpPF7KOOyHAjOPJeOAXycbXCRnTtyVlcpJ8WGP_k1Kouz_Rb_MDGiKvfROthHvfyTmHgNEuGcBy04tnmeJi4aSQw80p07bg0GqMqe45muPIB_UczbAVByyUNc-vsLAPl_iCMIId5s0PwxMNlOI4lK2kDXUe6vn-tMDaBL62tWeO43D5jl42unJ3VLWkDOn2qwXnQFZgYrgRyk8Ojm_iVXlaLJ_vx9q__w1y0L7Can3FnExKuWi_4HuT9TzZzHfLN7lt5rWo8WgG7pOn34iokCw9VvAgttSmttzXZlUo0mTJ70JMZORLe9F04js0xy99gRvxVtzsCU8Ai_TjBaRQ3h7MujRQvgHs8yzWEU-R2sAZMC63CLkehEyd_7m5tMYzScrm_CS8prGz3XARuawOATPYuruZkbzBzbnYHg4cRtaxvHZWhBtW4CT4XWL_FLm_tGkwMREUT1dzZX7IkfclAAHW_LpGqJD9Zx2TeB1BhJI7J_LLiRwX7n0OE_tVcKpIPE0Q0JNGnTLuIOU744K7nE9f-io0KYdc5AuNOL-ohmu1O23tbqXJicfYK-QRz3oufnewevv2T5zoXtHR2GZiY2z37nNHYv6k-lU8JbjCqyMZed0MeYArHuP3UnjEarWX-JmsGCIsMNsFOE2TabLCaex8PGKxr831KMh0_86Igadkt3RcCh2yHiVXQsdEMmO-vrFPk66qTOVlHKsUAGX6uNO8HqgGUmUZr7G6JAyt6sKdAMOz6KtgritYmdm2K73ZHvvF1rMH569-4eOSIxb6jEJyl8A5luTOJysiJcndvliVC_AOPISD6SDs9Tym98IpXeiUbcDAzN5mMzeNxylJq1wGhOk_ebXsDJ0M9HAEJBgW8xB8trIkvBmi2kps9TbiyZF4ROJ4pwSzPxD2jwgle7Kt8xP520-LIX5NnFQ5FWfwlOsP-NON-UwgfnjMhxJ72ijSZVGAW3RdVh7CdGnlWyCekMBbO6uvImlKTUKvYWLhRgjF3OVipmURCMdpXpKRH_ezu-koGTpOJnq_-Rej4IHhs"; // Замените на ваш Dropbox токен

    public String uploadFile(String localFilePath, String dropboxFolder) throws Exception {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-gui-uploader")
                .withUserLocaleFrom(Locale.getDefault())
                .build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        String dropboxPath = dropboxFolder + "/" + new File(localFilePath).getName();

        try (InputStream in = new FileInputStream(localFilePath)) {
            FileMetadata metadata = client.files().uploadBuilder(dropboxPath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);

            return "✅ File uploaded successfully to: " + metadata.getPathDisplay() + "\n";
        }
    }
}