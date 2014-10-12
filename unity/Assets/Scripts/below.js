#pragma strict

function Start () {

}

function Update () {
 
 if(transform.position.y < 0){
 	 Application.LoadLevel(Application.loadedLevel);
 }
}