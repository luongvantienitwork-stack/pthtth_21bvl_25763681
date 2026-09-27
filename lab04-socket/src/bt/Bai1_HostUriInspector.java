package bt;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class Bai1_HostUriInspector {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Loi: Thieu tham so. Vui long truyen hostname va URI.");
            return;
        }

        String hostname = args[0];
        String uriString = args[1];

        System.out.println("=== THONG TIN HOSTNAME ===");
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (InetAddress addr : addresses) {
                String ip = addr.getHostAddress();
                String type = (addr instanceof Inet4Address) ? "IPv4" : "IPv6";
                boolean isLoopback = addr.isLoopbackAddress();
                boolean isSiteLocal = addr.isSiteLocalAddress();

                System.out.printf("IP: %s | Loai: %s | Loopback: %b | Site Local: %b%n",
                        ip, type, isLoopback, isSiteLocal);
            }
        } catch (UnknownHostException e) {
            System.err.println("Loi: Khong the phan giai hostname: " + hostname);
        }

        System.out.println("\n=== THONG TIN URI ===");
        try {
            URI uri = new URI(uriString);
            System.out.println("Scheme: " + uri.getScheme());
            System.out.println("Host: " + uri.getHost());
            System.out.println("Port: " + uri.getPort());
            System.out.println("Path: " + uri.getPath());
            System.out.println("Query: " + uri.getQuery());
            System.out.println("Fragment: " + uri.getFragment());
        } catch (URISyntaxException e) {
            System.err.println("Loi: URI khong hop le: " + uriString);
        }
    }
}