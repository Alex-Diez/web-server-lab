package web.lab;

import co.unruly.matchers.OptionalMatchers;
import org.junit.Before;
import org.junit.Test;
import web.lab.logger.FileSystem;
import web.lab.logger.Record;
import web.lab.logger.URLLogger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static web.lab.logger.Action.CREATE;
import static web.lab.logger.Action.DELETE;
import static web.lab.logger.Action.MODIFY;

public class URLLoggerTest {

    private URLLogger logger;
    private FileSystem fileSystem;

    @Before
    public void setUp() throws Exception {
        fileSystem = new FileSystem();
        logger = new URLLogger(fileSystem);
    }

    @Test
    public void itShouldAddPageToURLLogger() throws Exception {
        LocalDateTime creationDate = logger.add("url");

        assertThat(logger.getLastModificationTimeFor("url"), OptionalMatchers.contains(creationDate));
    }

    @Test
    public void itShouldChangeURLModifiedTime() throws Exception {
        LocalDateTime creationTime = logger.add("url");
        LocalDateTime modificationTime = logger.update("url");

        assertThat(creationTime, lessThanOrEqualTo(modificationTime));
    }

    @Test
    public void itShouldReturnTheSameModificationTime() throws Exception {
        logger.add("url");
        LocalDateTime modificationTime = logger.update("url");

        assertThat(logger.getLastModificationTimeFor("url"), OptionalMatchers.contains(modificationTime));
    }

    @Test
    public void itShouldStoreOnFileSystemAfterAddingUrlToLogger() throws Exception {
        LocalDateTime creationDate = logger.add("url");

        assertThat(fileSystem.containsRecord(Optional.of(creationDate), "url", CREATE), is(true));
    }

    @Test
    public void itShouldNotStoreActionsWhichWereNotDone() throws Exception {
        assertThat(fileSystem.containsRecord(Optional.empty(), "url", DELETE), is(false));
    }

    @Test
    public void itShouldStoreModificationActionToFileSystem() throws Exception {
        LocalDateTime modificationDate = logger.update("url");

        assertThat(fileSystem.containsRecord(Optional.of(modificationDate), "url", MODIFY), is(true));
    }

    @Test
    public void itShouldNotStoreActionsWhichWereNotDoneWithMultipleUrls() throws Exception {
        logger.add("url-1");
        logger.add("url-2");
        logger.update("url-1");

        assertThat(fileSystem.containsRecord(Optional.empty(), "url-2", MODIFY), is(false));
    }

    @Test
    public void itShouldReturnListOfRecordsDoneWithUrl() throws Exception {
        logger.add("url");
        logger.update("url");
        logger.update("url");
        logger.update("url");
        logger.update("url");

        List<Record> records = fileSystem.listOfRecord("url");

        assertThat(records.size(), is(5));
    }
}
