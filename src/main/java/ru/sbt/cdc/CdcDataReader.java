package ru.sbt.cdc;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.GridComponent;
import org.apache.ignite.internal.binary.BinaryContext;
import org.apache.ignite.internal.binary.GridBinaryMarshaller;
import org.apache.ignite.internal.processors.cache.binary.CacheObjectBinaryProcessorImpl;
import org.apache.ignite.internal.processors.cache.persistence.wal.reader.StandaloneGridKernalContext;
import org.apache.ignite.internal.util.typedef.internal.U;
import ru.sbt.User;

public class CdcDataReader {
    public static void main(String[] args) throws Exception {
        String root, binaryMeta, marshaller;

        if (args == null || args.length == 0) {
            root = "/Users/sbt-izhikov-nv/tmp/cdc";
            binaryMeta = "/Users/sbt-izhikov-nv/bin/apache-ignite-2.13.0-SNAPSHOT-bin/work/db/binary_meta/node00-0aacd535-873b-494e-b389-322a02371ae8";
            marshaller = "/Users/sbt-izhikov-nv/bin/apache-ignite-2.13.0-SNAPSHOT-bin/work/db/marshaller";
        }
        else {
            root = args[0];
            binaryMeta = args[1];
            marshaller = args[2];
        }

        StandaloneGridKernalContext kctx = setupIgniteInternals(binaryMeta, marshaller);

        try {
            System.out.println("[CdcDataReader] Start read data[root=" + root + ']');

            File[] files = new File(root).listFiles();

            for (File file : files) {
                BinaryObject val;

                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(file))) {
                    val = (BinaryObject)is.readObject();
                }

                User user = val.deserialize();

                System.out.println("[CdcDataReader] Reading[file=" + file.getName() + ", user=" + user + ']');
            }
        }
        finally {
            for (GridComponent comp : kctx)
                comp.stop(false);
        }

    }

    private static StandaloneGridKernalContext setupIgniteInternals(String binaryMeta, String marshaller) throws IgniteCheckedException {
        IgniteLogger log = U.initLogger(new IgniteConfiguration(), "cdc-data-reader");

        StandaloneGridKernalContext kctx = new StandaloneGridKernalContext(
            log,
            new File(binaryMeta),
            new File(marshaller)
        );

        for (GridComponent comp : kctx)
            comp.start();

        BinaryContext ctx = ((CacheObjectBinaryProcessorImpl)kctx.cacheObjects()).binaryContext();

        GridBinaryMarshaller.popContext(ctx);

        return kctx;
    }
}
