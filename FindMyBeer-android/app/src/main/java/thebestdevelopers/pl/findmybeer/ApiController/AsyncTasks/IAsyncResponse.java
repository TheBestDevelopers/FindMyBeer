package thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks;

public interface IAsyncResponse {
    void processFinish(String result, Boolean timeout);
}
