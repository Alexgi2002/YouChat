package cu.alexgi.youchat.zip4j.headers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import cu.alexgi.youchat.zip4j.exception.ZipException;
import cu.alexgi.youchat.zip4j.model.FileHeader;
import cu.alexgi.youchat.zip4j.model.ZipModel;
import cu.alexgi.youchat.zip4j.util.InternalZipConstants;

import static cu.alexgi.youchat.zip4j.util.InternalZipConstants.ZIP4J_DEFAULT_CHARSET;
import static cu.alexgi.youchat.zip4j.util.InternalZipConstants.ZIP_STANDARD_CHARSET_NAME;
import static cu.alexgi.youchat.zip4j.util.Zip4jUtil.isStringNotNullAndNotEmpty;

public class HeaderUtil {

  public static FileHeader getFileHeader(ZipModel zipModel, String fileName) throws ZipException {
    FileHeader fileHeader = getFileHeaderWithExactMatch(zipModel, fileName);

    if (fileHeader == null) {
      fileName = fileName.replaceAll("\\\\", "/");
      fileHeader = getFileHeaderWithExactMatch(zipModel, fileName);

      if (fileHeader == null) {
        fileName = fileName.replaceAll("/", "\\\\");
        fileHeader = getFileHeaderWithExactMatch(zipModel, fileName);
      }
    }

    return fileHeader;
  }

  public static String decodeStringWithCharset(byte[] data, boolean isUtf8Encoded, Charset charset) {
    if (charset != null) {
      return new String(data, charset);
    }

    if (isUtf8Encoded) {
      return new String(data, InternalZipConstants.CHARSET_UTF_8);
    }

    try {
      return new String(data, ZIP_STANDARD_CHARSET_NAME);
    } catch (UnsupportedEncodingException e) {
      return new String(data);
    }
  }

  public static byte[] getBytesFromString(String string, Charset charset) {
    if (charset == null) {
      return string.getBytes(ZIP4J_DEFAULT_CHARSET);
    }

    return string.getBytes(charset);
  }

  public static long getOffsetStartOfCentralDirectory(ZipModel zipModel) {
    if (zipModel.isZip64Format()) {
      return zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber();
    }

    return zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public static List<FileHeader> getFileHeadersUnderDirectory(List<FileHeader> allFileHeaders, FileHeader rootFileHeader) {
    if (!rootFileHeader.isDirectory()) {
      return Collections.emptyList();
    }

    return allFileHeaders.stream().filter(e -> e.getFileName().startsWith(rootFileHeader.getFileName())).collect(Collectors.toList());
  }

  public static long getTotalUncompressedSizeOfAllFileHeaders(List<FileHeader> fileHeaders) {
    long totalUncompressedSize = 0;
    for (FileHeader fileHeader : fileHeaders) {
      if (fileHeader.getZip64ExtendedInfo() != null &&
          fileHeader.getZip64ExtendedInfo().getUncompressedSize() > 0) {
        totalUncompressedSize += fileHeader.getZip64ExtendedInfo().getUncompressedSize();
      } else {
        totalUncompressedSize += fileHeader.getUncompressedSize();
      }
    }
    return totalUncompressedSize;
  }

  private static FileHeader getFileHeaderWithExactMatch(ZipModel zipModel, String fileName) throws ZipException {
    if (zipModel == null) {
      throw new ZipException("zip model is null, cannot determine file header with exact match for fileName: "
          + fileName);
    }

    if (!isStringNotNullAndNotEmpty(fileName)) {
      throw new ZipException("file name is null, cannot determine file header with exact match for fileName: "
          + fileName);
    }

    if (zipModel.getCentralDirectory() == null) {
      throw new ZipException("central directory is null, cannot determine file header with exact match for fileName: "
          + fileName);
    }

    if (zipModel.getCentralDirectory().getFileHeaders() == null) {
      throw new ZipException("file Headers are null, cannot determine file header with exact match for fileName: "
          + fileName);
    }

    if (zipModel.getCentralDirectory().getFileHeaders().size() == 0) {
      return null;
    }

    for (FileHeader fileHeader : zipModel.getCentralDirectory().getFileHeaders()) {
      String fileNameForHdr = fileHeader.getFileName();
      if (!isStringNotNullAndNotEmpty(fileNameForHdr)) {
        continue;
      }

      if (fileName.equalsIgnoreCase(fileNameForHdr)) {
        return fileHeader;
      }
    }

    return null;
  }
}
