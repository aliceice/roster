package de.aice.roster.ratpack.util;

import org.apache.commons.lang3.StringUtils;
import ratpack.func.Action;
import ratpack.handling.Context;

import java.net.HttpURLConnection;
import java.util.function.Supplier;

/**
 * Response utils.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Response {

	/**
	 * Send the supplied content now.
	 *
	 * @param context  Ratpack context
	 * @param supplier Supplier of content
	 */
	public static void doSend(final Context context, final Supplier<String> supplier) {
		context.getResponse().send(supplier.get());
	}

	/**
	 * Create a "send http ok" action.
	 *
	 * @param context Ratpack context
	 * @return Http ok Action
	 */
	public static Action<? super String> sendOk(final Context context) {
		return s -> doSendOk(context);
	}

	/**
	 * Send a http ok now.
	 *
	 * @param context Ratpack context
	 */
	public static void doSendOk(final Context context) {
		doSendStatus(context, HttpURLConnection.HTTP_OK);
	}

	/**
	 * Create a "send http status" action.
	 *
	 * @param context Ratpack context
	 * @param code    status code to send
	 * @return http status action
	 */
	public static Action<? super String> sendStatus(final Context context, final int code) {
		return s -> doSendStatus(context, code);
	}

	/**
	 * Send http status now.
	 *
	 * @param context Ratpack context
	 * @param code    status code to send
	 */
	public static void doSendStatus(final Context context, final int code) {
		context.getResponse().status(code).send();
	}

	/**
	 * Default error handler of Ratpack Roster application.
	 *
	 * @param context Ratpack context
	 * @param <T>     type of Exception
	 * @return Error handler action
	 */
	public static <T extends Throwable> Action<T> defaultErrorHandler(final Context context) {
		return throwable -> context.getResponse().status(HttpURLConnection.HTTP_INTERNAL_ERROR)
		                           .send(StringUtils.defaultString(throwable.getMessage()));
	}

	private Response() { }
}
