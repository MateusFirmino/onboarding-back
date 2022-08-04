package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioMovimentacaoDto;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioPessoaDto;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioProdutoDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@AllArgsConstructor
@Service
public class ExportService {
    private final static Locale ptBr = new Locale("pt", "BR");

    public ByteArrayInputStream produtoPDFReport(List<RelatorioProdutoDto> produtos) {
        Document documento = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();

        try {
            PdfWriter writer = PdfWriter.getInstance(documento, out);
            writer.setPageEvent(event);

            documento.setPageSize(new RectangleReadOnly(1150, 1020));
            documento.open();
            String imgsrc = "images\\totem-logo.png";

            PdfPTable tabheader = new PdfPTable(new float[]{1, 4});
            tabheader.setWidthPercentage(100);
            PdfPCell cell;
            Font font1 = FontFactory
                    .getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
            Font font = FontFactory
                    .getFont(FontFactory.HELVETICA_BOLD, 24, new BaseColor(51, 51, 51));
            Font cellFont = FontFactory
                    .getFont(FontFactory.HELVETICA, 14, new BaseColor(51, 51, 51));
            Image img = Image.getInstance(imgsrc);
            img.scaleAbsolute(120, 110);
            cell = new PdfPCell(img);
            cell.setPaddingLeft(10);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthLeft(1);
            cell.setBorderWidthBottom(1);
            cell.setBorderWidthRight(0);
            tabheader.addCell(cell);

            cell.setPhrase(new Phrase("\nTOTEM TI - TREINAMENTO E CONSULTORIA EM TI\n" +
                    "\n" +
                    "GERENCIA DE DESENVOLVIMENTO E ANALISE DE SISTEMAS\n" +
                    "\n" +
                    "GERENCIA TÉCNICA\n" +
                    "\n" +
                    "SISTEMA ONBOARDING - TREINAMENTO\n\n", font1));
            cell.setBorderWidthTop(1);
            cell.setBorderWidthRight(1);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthBottom(1);
            tabheader.addCell(cell);
            documento.add(tabheader);

            //TEXTOS

            Paragraph para = new Paragraph(" ");
            documento.add(para);
            para = new Paragraph("RELATÓRIO DE PRODUTOS", font);
            para.setAlignment(Element.ALIGN_CENTER);
            documento.add(para);
            para = new Paragraph(" ");
            documento.add(para);

            PdfPTable table = new PdfPTable(new float[]{15, 142, 55, 23});
            table.setWidthPercentage(100);
            PdfPCell header1 = new PdfPCell();

//          titulos das colunas

            this.setBorder(header1, 1, 1, 0, 1);
            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.BLACK);
            this.addColuna(header1, table, "#");

            this.setBorder(header1, 1, 1, 0, 0);
            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Produto");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Categoria");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Valor");

            var cont = 0;
            for (var element : produtos) {
                cont++;
                //INDEX
                PdfPCell celdados = new PdfPCell(new Phrase(cont + "", cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                this.addCelula(celdados, table, cont, produtos.size());

                //NOME
                celdados = new PdfPCell(new Phrase(element.getNome(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_LEFT);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, produtos.size());

                //TIPO
                celdados = new PdfPCell(new Phrase(element.getCategoria(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, produtos.size());

                //EMAIL
                celdados = new PdfPCell(new Phrase(NumberFormat.getCurrencyInstance(ptBr).format(element.getValor()), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celdados.setBorderWidthRight(1);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, produtos.size());
            }
            documento.add(table);
            documento.close();
        } catch (
                DocumentException | IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    //  RELATÓRIO DE PESSOAS
    public ByteArrayInputStream pessoaPDFReport(List<RelatorioPessoaDto> pessoas) {
        Document documento = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();

        try {
            PdfWriter writer = PdfWriter.getInstance(documento, out);
            writer.setPageEvent(event);

            documento.setPageSize(new RectangleReadOnly(1150, 1020));
            documento.open();
            String imgsrc = "images\\totem-logo.png";

            PdfPTable tabheader = new PdfPTable(new float[]{1, 6});
            tabheader.setWidthPercentage(100);
            PdfPCell cell;
            Font font1 = FontFactory
                    .getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
            Font font = FontFactory
                    .getFont(FontFactory.HELVETICA_BOLD, 24, new BaseColor(51, 51, 51));
            Font cellFont = FontFactory
                    .getFont(FontFactory.HELVETICA, 14, new BaseColor(51, 51, 51));
            Image img = Image.getInstance(imgsrc);
            img.scaleAbsolute(120, 110);
            cell = new PdfPCell(img);
            cell.setPaddingLeft(10);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthLeft(1);
            cell.setBorderWidthBottom(1);
            cell.setBorderWidthRight(0);
            tabheader.addCell(cell);

            cell.setPhrase(new Phrase("\nTOTEM TI - TREINAMENTO E CONSULTORIA EM TI\n" +
                    "\n" +
                    "GERENCIA DE DESENVOLVIMENTO E ANALISE DE SISTEMAS\n" +
                    "\n" +
                    "GERENCIA TÉCNICA\n" +
                    "\n" +
                    "SISTEMA ONBOARDING - TREINAMENTO\n\n", font1));
            cell.setBorderWidthTop(1);
            cell.setBorderWidthRight(1);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthBottom(1);
            tabheader.addCell(cell);
            documento.add(tabheader);

            //TEXTOS

            Paragraph para = new Paragraph(" ");
            documento.add(para);
            para = new Paragraph("RELATÓRIO DE CLIENTES", font);
            para.setAlignment(Element.ALIGN_CENTER);
            documento.add(para);
            para = new Paragraph(" ");
            documento.add(para);

            PdfPTable table = new PdfPTable(new float[]{5, 35, 10, 15, 15, 20});
            table.setWidthPercentage(100);
            PdfPCell header1 = new PdfPCell();

//          titulos das colunas

            this.setBorder(header1, 1, 1, 0, 1);
            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.BLACK);
            this.addColuna(header1, table, "#");

            this.setBorder(header1, 1, 1, 0, 0);
            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Nome");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Tipo Pessoa");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Município");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Celular");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "E-mail");

            var cont = 0;
            for (var pessoa : pessoas) {
                cont++;
                //INDEX
                PdfPCell celdados = new PdfPCell(new Phrase(cont + "", cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                this.addCelula(celdados, table, cont, pessoas.size());

                //NOME
                celdados = new PdfPCell(new Phrase(pessoa.getNome(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_LEFT);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, pessoas.size());

                //TIPO
                celdados = new PdfPCell(new Phrase(String.valueOf(pessoa.getTipoPessoa()), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, pessoas.size());

                //MUNICIPIO
                celdados = new PdfPCell(new Phrase(pessoa.getMunicipio(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, pessoas.size());

                //CONTATO
                celdados = new PdfPCell(new Phrase(pessoa.getContato(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, pessoas.size());

                //EMAIL
                celdados = new PdfPCell(new Phrase(pessoa.getEmail(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_LEFT);
                celdados.setBorderWidthRight(1);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, pessoas.size());
            }
            documento.add(table);
            documento.close();
        } catch (
                DocumentException | IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    //  RELATORIO DE VENDAS
    public ByteArrayInputStream vendasReportPDF(List<RelatorioMovimentacaoDto> movimentacaoDiaria) {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            writer.setPageEvent(event);

            doc.setPageSize(new RectangleReadOnly(1150, 1020));
            doc.open();
            String imgsrc = "images\\totem-logo.png";

            PdfPTable tabheader = new PdfPTable(new float[]{1, 7});
            tabheader.setWidthPercentage(100);
            PdfPCell cell;
            Font font1 = FontFactory
                    .getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
            Font font = FontFactory
                    .getFont(FontFactory.HELVETICA_BOLD, 24, new BaseColor(51, 51, 51));
            Font cellFont = FontFactory
                    .getFont(FontFactory.HELVETICA, 14, new BaseColor(51, 51, 51));
            Image img = Image.getInstance(imgsrc);
            img.scaleAbsolute(120, 110);
            cell = new PdfPCell(img);
            cell.setPaddingLeft(10);
            cell.setBorderWidthTop(1);
            cell.setBorderWidthLeft(1);
            cell.setBorderWidthBottom(1);
            cell.setBorderWidthRight(0);
            tabheader.addCell(cell);

            cell.setPhrase(new Phrase("\nTOTEM TI - TREINAMENTO E CONSULTORIA EM TI\n" +
                    "\n" +
                    "GERENCIA DE DESENVOLVIMENTO E ANALISE DE SISTEMAS\n" +
                    "\n" +
                    "GERENCIA TÉCNICA\n" +
                    "\n" +
                    "SISTEMA ONBOARDING - TREINAMENTO\n\n", font1));
            cell.setBorderWidthTop(1);
            cell.setBorderWidthRight(1);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthBottom(1);
            tabheader.addCell(cell);
            doc.add(tabheader);

            //TEXTOS

            Paragraph para = new Paragraph(" ");
            doc.add(para);
            para = new Paragraph("RELATÓRIO MOVIMENTAÇÃO DIÁRIA", font);
            para.setAlignment(Element.ALIGN_CENTER);
            doc.add(para);
            para = new Paragraph(" ");
            doc.add(para);

            PdfPTable table = new PdfPTable(new float[]{21, 33, 68, 22, 22, 33, 27});
            table.setWidthPercentage(100);
            PdfPCell header1 = new PdfPCell();

//          titulos das colunas
            this.setBorder(header1, 1, 1, 0, 1);
            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.BLACK);
            this.addColuna(header1, table, "Nr Venda");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Data");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Produto");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Qtd");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Valor (R$)");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Vendedor");

            this.setBorderColor(header1, BaseColor.BLACK, BaseColor.BLACK, BaseColor.WHITE, BaseColor.WHITE);
            this.addColuna(header1, table, "Cliente");

//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", ptBr);
            var cont = 0;
            var valorTotal = 0.0;
            for (var element : movimentacaoDiaria) {
                cont++;
                valorTotal = valorTotal + Float.parseFloat(String.valueOf(element.getValor()));
                //INDEX
                PdfPCell celdados = new PdfPCell(new Phrase(cont + "", cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
                //Data
                celdados = new PdfPCell(new Phrase(String.valueOf(element.getData())));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
                //Produto
                celdados = new PdfPCell(new Phrase(element.getProduto(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_LEFT);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
                //Qtd
                celdados = new PdfPCell(new Phrase(element.getQtd() + ""));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
                //Valor
                celdados = new PdfPCell(new Phrase(NumberFormat.getCurrencyInstance(ptBr).format(element.getValor().intValue() / 100)));
                celdados.setHorizontalAlignment(Element.ALIGN_RIGHT);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
                //Cliente
                celdados = new PdfPCell(new Phrase(element.getVendedor(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
                //Vendedor
                celdados = new PdfPCell(new Phrase(element.getCliente(), cellFont));
                celdados.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdados.setBorderWidthRight(1);
                celdados.setBorderColorLeft(BaseColor.WHITE);
                this.addCelula(celdados, table, cont, movimentacaoDiaria.size());
            }
            doc.add(table);
            para = new Paragraph(" ");
            doc.add(para);
            para = new Paragraph(" ");
            doc.add(para);
            PdfPTable t2 = new PdfPTable(new float[]{22, 3});
            t2.setWidthPercentage(100);
            PdfPCell h2 = new PdfPCell();
            h2.setBorderWidth(1);
            h2.setBorderColor(BaseColor.WHITE);
            com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 14, new BaseColor(51, 51, 51));
            h2.setBackgroundColor(new BaseColor(200, 219, 236));
            h2.setHorizontalAlignment(Element.ALIGN_LEFT);
            h2.setPaddingBottom(5);
            h2.setPaddingTop(5);
            h2.setPhrase(new Phrase("TOTAL GERAL", headFont));
            t2.addCell(h2);

            this.addColuna(h2, t2, NumberFormat.getCurrencyInstance(ptBr).format(valorTotal / 100));
            doc.add(t2);
            doc.close();
        } catch (
                DocumentException | IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public void addCelula(PdfPCell cellName, PdfPTable table, Integer cont, Integer tam) {
        cellName.setBorderWidthLeft(1);
        cellName.setBorderWidth(0);
        cellName.setPaddingTop(5);
        cellName.setPaddingBottom(5);
        cellName.setVerticalAlignment(Element.ALIGN_MIDDLE);
        if (cont % 2 == 0) {
            cellName.setBackgroundColor(new BaseColor(242, 242, 242));
        }
        if (cont.equals(tam)) {
            cellName.setBorderWidthBottom(1);
            cellName.setBorderColorBottom(BaseColor.BLACK);
        }
        table.addCell(cellName);
    }

    public void addColuna(PdfPCell collName, PdfPTable table, String titulo) {
        collName.setBorderWidth(0);
        com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 14, new BaseColor(51, 51, 51));
        collName.setBackgroundColor(new BaseColor(200, 219, 236));
        collName.setHorizontalAlignment(Element.ALIGN_CENTER);
        collName.setPaddingBottom(5);
        collName.setPaddingTop(5);
        collName.setPhrase(new Phrase(titulo, headFont));
        table.addCell(collName);
    }

    public void setBorder(PdfPCell pdfcell, Integer top, Integer right, Integer bottom, Integer left) {
        pdfcell.setBorderWidthTop(top);
        pdfcell.setBorderWidthRight(right);
        pdfcell.setBorderWidthBottom(bottom);
        pdfcell.setBorderWidthLeft(left);
    }

    public void setBorderColor(PdfPCell pdfcell, BaseColor top, BaseColor right, BaseColor bottom, BaseColor left) {
        pdfcell.setBorderColorTop(top);
        pdfcell.setBorderColorRight(right);
        pdfcell.setBorderColorBottom(bottom);
        pdfcell.setBorderColorLeft(left);
    }

    public static class HeaderFooterPageEvent extends PdfPageEventHelper {

        public void onStartPage(PdfWriter writer, Document document) {
//            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
//            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            String imgsrc = "images\\totem-logo-footer.png";

            try {
                Image img = Image.getInstance(imgsrc);
                img.setAlignment(Element.ALIGN_CENTER);
                img.setAbsolutePosition(20, 15);
                img.setBackgroundColor(BaseColor.WHITE);
                img.scaleAbsolute(140, 36);
                writer.getDirectContent().addImage(img);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
//            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("TOTEM-TI-LOGO"), 70, 30, 0);
            Date d = new Date();
            Locale local = new Locale("pt", "BR");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", local);
            SimpleDateFormat hora = new SimpleDateFormat("HH", local);
            SimpleDateFormat min = new SimpleDateFormat("mm", local);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Impresso por Admin em " + sdf.format(d) + " às " +
                    hora.format(d) + "h" + min.format(d) + "min. "), 1110, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Página " + document.getPageNumber()), 1110, 15, 0);
        }
    }
}


