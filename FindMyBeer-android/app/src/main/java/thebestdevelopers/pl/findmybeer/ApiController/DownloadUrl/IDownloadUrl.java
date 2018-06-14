package thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl;

import java.io.IOException;

public interface IDownloadUrl {
    String readUrl(String myUrl) throws IOException;
}
