using UnityEngine;
using System.Collections;
using System.IO;



public class read : MonoBehaviour {

	public Transform brick;
	public Transform flame;
	public float z = 5.2f; 
	float temp = 0f;

	// Use this for initialization
	void Start () {
		    Instantiate(brick, new Vector3(0, -0.2f, -2), Quaternion.identity);
			// Create an instance of StreamReader to read from a file. 
			// The using statement also closes the StreamReader. 
			using (StreamReader sr = new StreamReader("asd.txt")) 
			{
				string line;
				// Read and display lines from the file until the end of  
				// the file is reached. 
				while ((line = sr.ReadLine()) != null) 
				{
				    Instantiate(brick, new Vector3(0, float.Parse(line), z-2), Quaternion.identity);
					z+=5.2f;
					temp = float.Parse(line); 
				}

			}
		Instantiate(flame, new Vector3(0, temp, z-7.2f), Quaternion.identity);
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
