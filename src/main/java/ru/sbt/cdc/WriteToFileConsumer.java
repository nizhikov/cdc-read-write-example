package ru.sbt.cdc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cdc.CdcConsumer;
import org.apache.ignite.cdc.CdcEvent;
import org.apache.ignite.internal.processors.metric.MetricRegistry;
import org.apache.ignite.resources.LoggerResource;

/**
 * Consumer writes each event to file.
 */
public class WriteToFileConsumer implements CdcConsumer {
    @LoggerResource
    private IgniteLogger log;

    /** Root directory. */
    private File root;

    @Override public void start(MetricRegistry mreg) {
        root.mkdirs();
    }

    @Override public boolean onEvents(Iterator<CdcEvent> events) {
        events.forEachRemaining(e -> {
            File file = new File(root, e.key() + ".bin");

            BinaryObject obj = (BinaryObject)e.value();

            try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
                os.writeObject(e.value());
            }
            catch (IOException err) {
                throw new RuntimeException(err);
            }

            log.info("Event saved to file[fio=" + obj.field("fio") + ", file=" + file + ']');
        });

        return true;
    }

    @Override public void stop() {
        // No-op.
    }

    public void setRoot(File root) {
        this.root = root;
    }
}
