#pragma strict


var nextActionTime = 0.0f;
var period = 1.1f;


function Start () {

}

function Update () {
//    var MoveForward = Input.GetAxis("Vertical")  * 20 * Time.deltaTime;
//
//    if (Time.time > nextActionTime ) {
//       nextActionTime += period;
//        Debug.Log("lqlq");
//        transform.Translate(Vector3.forward * MoveForward);
//    }
 transform.Translate(Vector3.back * Time.deltaTime * 3);
}