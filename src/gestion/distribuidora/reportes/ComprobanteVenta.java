package gestion.distribuidora.reportes;

import gestion.distribuidora.modelo.Cliente;
import gestion.distribuidora.modelo.DetalleVenta;
import gestion.distribuidora.modelo.Venta;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Comprobante de una venta para imprimir, hecho con la API de impresion de Java
 * (java.awt.print), sin librerias externas. En Windows se puede elegir la
 * impresora "Microsoft Print to PDF" para guardarlo como PDF.
 */
public class ComprobanteVenta implements Printable {

    private static final NumberFormat MONEDA = NumberFormat.getCurrencyInstance(Locale.of("es", "AR"));
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Font TITULO = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Font NEGRITA = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    private static final Font NORMAL = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    private static final int ALTO_RENGLON = 16;
    private static final int MARGEN = 40;   // algunas impresoras (como la de PDF) no dejan margen propio

    private final Venta venta;

    /**
     * Prepara el comprobante de una venta.
     * @param venta la venta, con su cliente y sus renglones
     */
    public ComprobanteVenta(Venta venta) {
        this.venta = venta;
    }

    /**
     * Abre el dialogo de impresion y, si el usuario acepta, imprime el comprobante.
     * @return true si se mando a imprimir, false si el usuario cancelo
     * @throws ImpresionException si la impresora dio un error
     */
    public boolean imprimir() throws ImpresionException {
        PrinterJob trabajo = PrinterJob.getPrinterJob();
        trabajo.setJobName("Comprobante venta " + venta.getId());
        trabajo.setPrintable(this);
        if (!trabajo.printDialog()) {
            return false;
        }
        try {
            trabajo.print();
            return true;
        } catch (PrinterException e) {
            throw new ImpresionException("No se pudo imprimir el comprobante: " + e.getMessage(), e);
        }
    }

    /**
     * Dibuja una pagina del comprobante. Lo llama el sistema de impresion.
     * @param g donde se dibuja la pagina
     * @param formato el tamano y los margenes de la hoja
     * @param pagina el numero de pagina, empezando en 0
     * @return PAGE_EXISTS si la pagina tiene contenido, NO_SUCH_PAGE si no
     */
    @Override
    public int print(Graphics g, PageFormat formato, int pagina) {
        int renglonesPorPagina = Math.max(1, (int) ((formato.getImageableHeight() - 2 * MARGEN - 220) / ALTO_RENGLON));
        List<DetalleVenta> detalles = venta.getDetalles();
        int desde = pagina * renglonesPorPagina;
        if (desde > 0 && desde >= detalles.size()) {
            return NO_SUCH_PAGE;
        }
        int hasta = Math.min(detalles.size(), desde + renglonesPorPagina);

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(formato.getImageableX() + MARGEN, formato.getImageableY() + MARGEN);
        int ancho = (int) formato.getImageableWidth() - 2 * MARGEN;
        int y = dibujarEncabezado(g2, ancho, pagina);
        y = dibujarTabla(g2, ancho, y, detalles.subList(desde, hasta));
        if (hasta == detalles.size()) {
            dibujarTotal(g2, ancho, y);
        }
        return PAGE_EXISTS;
    }

    private int dibujarEncabezado(Graphics2D g, int ancho, int pagina) {
        int y = 20;
        g.setFont(TITULO);
        g.drawString("Distribuidora de Ferretería", 0, y);
        g.setFont(NEGRITA);
        y += 22;
        g.drawString("Comprobante de venta N° " + venta.getId(), 0, y);
        derecha(g, "Página " + (pagina + 1), ancho, y);
        g.setFont(NORMAL);
        y += ALTO_RENGLON;
        g.drawString("Fecha: " + venta.getFecha().format(FECHA) + "     Estado: " + venta.getEstado(), 0, y);
        Cliente c = venta.getCliente();
        y += ALTO_RENGLON * 2;
        g.setFont(NEGRITA);
        g.drawString("Cliente: " + c.getNombreApellido(), 0, y);
        g.setFont(NORMAL);
        y += ALTO_RENGLON;
        g.drawString("CUIT: " + c.getCuit() + "     Dirección: " + texto(c.getDireccion())
                + "     Teléfono: " + texto(c.getTelefono()), 0, y);
        return y + ALTO_RENGLON * 2;
    }

    private int dibujarTabla(Graphics2D g, int ancho, int y, List<DetalleVenta> renglones) {
        int[] col = columnas(ancho);
        g.setFont(NEGRITA);
        g.drawString("Código", col[0], y);
        g.drawString("Artículo", col[1], y);
        derecha(g, "Cant.", col[3] - 10, y);
        derecha(g, "Precio unit.", col[4] - 10, y);
        derecha(g, "Subtotal", ancho, y);
        g.drawLine(0, y + 4, ancho, y + 4);
        g.setFont(NORMAL);
        for (DetalleVenta d : renglones) {
            y += ALTO_RENGLON;
            g.drawString(d.getProducto().getCodigo(), col[0], y);
            g.drawString(recortar(g, d.getProducto().getNombre(), col[2] - col[1]), col[1], y);
            derecha(g, String.valueOf(d.getCantidad()), col[3] - 10, y);
            derecha(g, MONEDA.format(d.getPrecioUnitario()), col[4] - 10, y);
            derecha(g, MONEDA.format(d.getSubtotal()), ancho, y);
        }
        return y + 6;
    }

    private void dibujarTotal(Graphics2D g, int ancho, int y) {
        g.drawLine(0, y, ancho, y);
        g.setFont(NEGRITA);
        y += ALTO_RENGLON;
        derecha(g, "TOTAL:  " + MONEDA.format(venta.getTotal()), ancho, y);
    }

    /** Posicion x donde empieza cada columna: codigo, articulo, cantidad, precio y subtotal. */
    private int[] columnas(int ancho) {
        return new int[]{0, (int) (ancho * 0.15), (int) (ancho * 0.55), (int) (ancho * 0.66), (int) (ancho * 0.83)};
    }

    /** Corta el texto con "..." si no entra en el ancho disponible. */
    private String recortar(Graphics2D g, String texto, int anchoMaximo) {
        FontMetrics fm = g.getFontMetrics();
        if (fm.stringWidth(texto) <= anchoMaximo) {
            return texto;
        }
        while (!texto.isEmpty() && fm.stringWidth(texto + "...") > anchoMaximo) {
            texto = texto.substring(0, texto.length() - 1);
        }
        return texto + "...";
    }

    private void derecha(Graphics2D g, String texto, int xDerecha, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, xDerecha - fm.stringWidth(texto), y);
    }

    private String texto(String valor) {
        return (valor == null || valor.isBlank()) ? "-" : valor;
    }
}
