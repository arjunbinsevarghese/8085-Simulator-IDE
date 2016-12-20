import java.io.File;
import java.io.FilenameFilter;

class OnlyExt implements FilenameFilter {
	String ext;

	public OnlyExt(String ext) {

		this.ext=ext;
	}

	public boolean accept(File dir, String name) {

		return name.startsWith(ext);
	}
}