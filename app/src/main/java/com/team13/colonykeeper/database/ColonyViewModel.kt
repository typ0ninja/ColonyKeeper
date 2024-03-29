package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.URI

class ColonyViewModel(private val repository: ColonyRepository): ViewModel() {
    //Yard Items

    private var yardNameInProgress = "";
    private var yardPhotoInProgress: Uri? = null;

    private var hiveNameInProgress = ""
    private var hiveQueenDateInProgress = ""
    private var hivePhotoInProgress: Uri? = null

    private var editHiveName = ""
    private var editHivePhotoInProgress: Uri? = Uri.EMPTY

    private var editYardName = ""
    private var editYardPhotoInProgress: Uri? = Uri.EMPTY

    private var inspectionNotes = ""

    var reportName: String = ""

    fun setYardNameInProgress(yardName: String){
        yardNameInProgress = yardName
    }
    fun resetYardNameInProgress(){
        yardNameInProgress = ""
    }

    fun getYardNameInProgress(): String{
        return yardNameInProgress
    }


    fun setYardPictureInProgress(yardPicture: Uri){
        yardPhotoInProgress = yardPicture
    }
    fun resetYardPictureInProgress(){
        yardPhotoInProgress = null
    }

    fun getYardPictureInProgress(): Uri?{
        return yardPhotoInProgress
    }



    fun setHiveNameInProgress(hiveName: String){
        hiveNameInProgress = hiveName
    }
    fun resetHiveNameInProgress(){
        hiveNameInProgress = ""
    }

    fun getHiveNameInProgress(): String{
        return hiveNameInProgress
    }

    fun setHiveQueenDateInProgress(hiveQueenDate: String){
        hiveQueenDateInProgress = hiveQueenDate
    }
    fun resetHiveQueenDateInProgress(){
        hiveQueenDateInProgress = ""
    }

    fun getHiveQueenDateInProgress(): String{
        return hiveQueenDateInProgress
    }


    fun setHivePictureInProgress(hivePicture: Uri){
        hivePhotoInProgress = hivePicture
    }
    fun resetHivePictureInProgress(){
        hivePhotoInProgress = null
    }

    fun getHivePictureInProgress(): Uri?{
        return hivePhotoInProgress
    }


    fun setEditYardNameInProgress(yardName: String){
        editYardName = yardName
    }
    fun resetEditYardNameInProgress(){
        editYardName = ""
    }

    fun getEditYardNameInProgress(): String{
        return editYardName
    }


    fun setEditYardPictureInProgress(yardPicture: Uri){
        editYardPhotoInProgress = yardPicture
    }
    fun resetEditYardPictureInProgress(){
        editYardPhotoInProgress = Uri.EMPTY
    }

    fun getEditYardPictureInProgress(): Uri?{
        return editYardPhotoInProgress
    }

    fun getYardHives(yard_id: Int): LiveData<List<Hive>> = repository.getYardHives(yard_id).asLiveData()

    fun setEditHiveNameInProgress(hiveName: String){
        editHiveName = hiveName
    }
    fun resetEditHiveNameInProgress(){
        editHiveName = ""
    }

    fun getEditHiveNameInProgress(): String{
        return editHiveName
    }


    fun setEditHivePictureInProgress(hivePicture: Uri){
        editHivePhotoInProgress = hivePicture
    }
    fun resetEditHivePictureInProgress(){
        editHivePhotoInProgress = Uri.EMPTY
    }

    fun getEditHivePictureInProgress(): Uri?{
        return editHivePhotoInProgress
    }


    fun getInspectionNotes(): String{
        return inspectionNotes
    }

    fun resetInspectionNotes(){
        inspectionNotes = ""
    }

    fun setInspectionNotes(inspectionNote: String){
        inspectionNotes = inspectionNote
    }

    fun deleteHive(hive_id: Int) = viewModelScope.launch { repository.deleteHive(hive_id) }

    fun deleteYardHives(yard_ID: Int) = viewModelScope.launch { repository.deleteYardHives(yard_ID) }

    fun deleteYard(yard_id: Int) = viewModelScope.launch { repository.deleteYard(yard_id) }


    fun allYards(): LiveData<List<Yard>> = repository.allYards.asLiveData()

    fun insertYard(yard: Yard) = viewModelScope.launch {
        repository.insertYard(yard)
    }

    fun getYard(yard_id: Int): LiveData<Yard> = repository.getYard(yard_id).asLiveData()

    //Hive Items
    fun allHives(): LiveData<List<Hive>> = repository.allHives.asLiveData()

    fun getHive(hiveID: Int): LiveData<Hive> = repository.getHive(hiveID).asLiveData()

    fun hivesFromYard(yardID: Int): LiveData<List<Hive>> = repository.hivesFromYard(yardID).asLiveData()

    fun getAllScheduled(): LiveData<List<Scheduled>> = repository.getAllScheduled().asLiveData()

    fun getScheduled(scheduled_id: Int): LiveData<Scheduled> = repository.getScheduled(scheduled_id).asLiveData()

    fun insertHive(hive: Hive) = viewModelScope.launch {
        repository.insertHive(hive)
    }

    fun updateHiveName(hive_id: Int, newName: String) = viewModelScope.launch{
        repository.updateHiveName(hive_id, newName)
    }

    fun getYardScheduled(yard_id: Int): LiveData<List<Scheduled>> = repository.getYardScheduled(yard_id).asLiveData()

    fun getTagScheduled(tag: String): LiveData<List<Scheduled>> = repository.getTagScheduled(tag).asLiveData()

    fun deleteScheduled(scheduled: Scheduled) = viewModelScope.launch {
        repository.deleteScheduled(scheduled)
    }

    fun deleteScheduled(scheduled_id: Int) = viewModelScope.launch {
        repository.deleteScheduled(scheduled_id)
    }

    fun deleteYardScheduled(yard_id: Int) = viewModelScope.launch {
        repository.deleteYardScheduled(yard_id)
    }

    fun deleteTagScheduled(tag: String) = viewModelScope.launch {
        repository.deleteTagScheduled(tag)
    }

    fun updateHivePhoto(hive_id: Int, photoURI: Uri) = viewModelScope.launch {
        repository.updateHivePhoto(hive_id, photoURI)
    }

    fun updateYardPhoto(yard_id: Int, photoURI: Uri) = viewModelScope.launch {
        repository.updateYardPhoto(yard_id, photoURI)
    }

    fun updateYardName(yard_id: Int, newName: String) = viewModelScope.launch {
        repository.updateYardName(yard_id, newName)
    }

    fun scheduleInspection(scheduled: Scheduled) = viewModelScope.launch {
        repository.scheduleInspection(scheduled)
    }

    fun updateInspection(newName: String, isNotif: Boolean, id: Int) = viewModelScope.launch {
        repository.updateInspection(newName, isNotif, id)
    }

    fun addInspection(newInspection: Inspections) = viewModelScope.launch {
        repository.addInspection(newInspection)
    }

    fun deleteInspection(inspection: Inspections) = viewModelScope.launch {
        repository.deleteInspection(inspection)
    }

    fun getInspections(): LiveData<List<Inspections>> = repository.getInspections().asLiveData()

    fun getGPS(yard: Yard): Pair<Double, Double>{
        return Pair<Double, Double>(yard.latitude, yard.longitude)
    }

    fun getGPS(hive: Hive): Pair<Double, Double>{
        var latitude: Double = getYard(hive.yardID).value!!.latitude
        var longitude: Double = getYard(hive.yardID).value!!.longitude

        return Pair<Double, Double>(latitude, longitude)
    }


}

class ColonyViewModelFactory(private val repository: ColonyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColonyViewModel::class.java)){
            return ColonyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}