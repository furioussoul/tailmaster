package esform.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.UUID;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Component
public class LoggerFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String requestId = UUID.randomUUID().toString();
        String uri = ((HttpServletRequest) request).getRequestURI();
        String method = ((HttpServletRequest) request).getMethod();
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        String paramsString = "";
        try {
            if (isPost(method)) {
                paramsString = getApplicationJsonBodyString(requestWrapper.getReader());
            } else if (isGet(method)) {
                paramsString = new ObjectMapper().writeValueAsString(requestWrapper.getParameterMap());
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            LOGGER.error("requestId:[{}],Exception", requestId);
        }

        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        String responseBody = "";
        try {
            chain.doFilter(requestWrapper, responseWrapper);
            responseBody = new String(responseWrapper.getCopy());
        } catch (Exception e) {
            responseBody = "systemError" + e.getMessage();
            LOGGER.error("requestId:[{}],Exception", requestId);
        }
        LOGGER.info("requestId:[{}]," +
                        "method:{}," +
                        "requestUrl:{}," +
                        "params:{}," +
                        "status:{}," +
                        "result:{}",
                requestId,
                method,
                uri,
                paramsString,
                responseWrapper.getStatus(),
                responseBody);
    }

    private boolean isPost(String method) {
        return method.equals("POST");
    }

    private boolean isGet(String method) {
        return method.equals("GET");
    }

    private String getApplicationJsonBodyString(BufferedReader br) {
        StringBuilder builder = new StringBuilder();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return builder.toString();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    public class RequestWrapper extends HttpServletRequestWrapper {

        private byte[] buff;

        public RequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            InputStream ins = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buf = new byte[1024];
            int len;
            while ((len = ins.read(buf)) > -1) {
                sb.append(new String(buf, 0, len));
            }
            buff = sb.toString().getBytes();
        }

        @Override
        public ServletInputStream getInputStream() {

            if (buff == null) {
                buff = new byte[0];
            }

            final ByteArrayInputStream inputStream = new ByteArrayInputStream(buff);

            return new ServletInputStream() {

                @Override
                public int read() throws IOException {
                    return inputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {

                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }
    }


    public class ResponseWrapper extends HttpServletResponseWrapper {

        private ServletOutputStream outputStream;
        private PrintWriter writer;

        private ServletOutputStreamCopier coyoteOutputStreamAndCopy;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            Assert.isNull(writer, "getWriter() has already been called on this response.");
            if (outputStream == null) {
                outputStream = getResponse().getOutputStream();
                coyoteOutputStreamAndCopy = new ServletOutputStreamCopier(outputStream);
            }

            return coyoteOutputStreamAndCopy;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            Assert.isNull(outputStream, "getOutputStream() has already been called on this response.");
            if (writer == null) {
                coyoteOutputStreamAndCopy = new ServletOutputStreamCopier(getResponse().getOutputStream());
                writer = new PrintWriter(new OutputStreamWriter(coyoteOutputStreamAndCopy, getResponse().getCharacterEncoding()), true);
            }

            return writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            } else if (outputStream != null) {
                coyoteOutputStreamAndCopy.flush();
            }
        }

        public byte[] getCopy() {
            if (coyoteOutputStreamAndCopy != null) {
                return coyoteOutputStreamAndCopy.getCopy();
            } else {
                return new byte[0];
            }
        }
    }

    public class ServletOutputStreamCopier extends ServletOutputStream {

        private OutputStream outputStream;
        private ByteArrayOutputStream outputStreamCopy;

        public ServletOutputStreamCopier(OutputStream outputStream) {
            Assert.notNull(outputStream, "ServletOutputStreamCopier outputStream is null");
            this.outputStream = outputStream;
            this.outputStreamCopy = new ByteArrayOutputStream(1024);
        }

        @Override
        public void flush() throws IOException {
            outputStream.flush();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            outputStreamCopy.write(b);
        }

        public byte[] getCopy() {
            return outputStreamCopy.toByteArray();
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }
}
