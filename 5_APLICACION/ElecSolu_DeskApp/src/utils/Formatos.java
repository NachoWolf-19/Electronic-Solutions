package utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Formatos {
	// Formato Decimal
	private static final DecimalFormat df = new DecimalFormat("0.00");
	// Formatos de Fecha y Hora
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter FORMATO_HORA_24H = DateTimeFormatter.ofPattern("HH:mm");
	private static final DateTimeFormatter FORMATO_HORA_12H = DateTimeFormatter.ofPattern("hh:mm a");
	private static final DateTimeFormatter FORMATO_FECHA_HORA_24H = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static final DateTimeFormatter FORMATO_FECHA_HORA_12H = DateTimeFormatter
			.ofPattern("EEEE, dd/MM/yyyy hh:mm a");

	// Formato Soles
	public static String soles(double price) {
		return "S/" + df.format(price);
	}

	// Formato fecha estandar (día/mes/año)
	public static String fecha(LocalDate date) {
		return date.format(FORMATO_FECHA);
	}

	// Formato hora 24h (hora:minuto)
	public static String hora24(LocalTime time) {
		return time.format(FORMATO_HORA_24H);
	}

	// Formato hora 12h (hora:minuto am/pm)
	public static String hora12(LocalTime time) {
		return time.format(FORMATO_HORA_12H);
	}

	// Formato fecha y hora 24h (día/mes/año hora:minuto:segundo)
	public static String fechaHora24(LocalDateTime dateTime) {
		return dateTime.format(FORMATO_FECHA_HORA_24H);
	}

	// Formato fecha y hora 12h (nombre del día, día/mes/año hora:minuto am/pm)
	public static String fechaHora12(LocalDateTime dateTime) {
		return dateTime.format(FORMATO_FECHA_HORA_12H);
	}
}
