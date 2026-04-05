package com.moneygest.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.moneygest.Repository.InformeRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InformeService {

    private final InformeRepository informeRepository;

    public InformeService(InformeRepository informeRepository) {
        this.informeRepository = informeRepository;
    }


    public double totalI() { return informeRepository.sumaTotalIngresosCapital(); }
    public double totalV() { return informeRepository.sumaTotalVentas(); }
    public double totalG() { return informeRepository.sumaTotalGastos(); }


    public double obtenerSaldoReal() {
        return (totalI() + totalV()) - totalG();
    }

    public void descargarPdf(HttpServletResponse response, LocalDateTime inicio, LocalDateTime fin) throws IOException {
        List<Object[]> datos = informeRepository.obtenerMovimientosUnificados(inicio, fin);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("MONEYGEST - ESTADO DE CUENTA"));
        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Fecha");
        tabla.addCell("Tipo");
        tabla.addCell("Detalle");
        tabla.addCell("Monto");

        DecimalFormat df = new DecimalFormat("#,##0.00");

        for (Object[] fila : datos) {
            tabla.addCell(fila[0].toString());
            tabla.addCell(fila[1].toString());
            tabla.addCell(fila[2].toString());


            double montoFila = ((Number) fila[3]).doubleValue();
            tabla.addCell("$ " + df.format(montoFila));
        }

        document.add(tabla);
        document.add(new Paragraph(" "));


        document.add(new Paragraph("SALDO TOTAL DISPONIBLE: $ " + df.format(obtenerSaldoReal())));
        document.close();
    }
}