package structs;

//06/05/2019
public interface ProjectSerializer {
	public byte [] serialize(Project project);
	public Project deserialize(byte [] data);
}
