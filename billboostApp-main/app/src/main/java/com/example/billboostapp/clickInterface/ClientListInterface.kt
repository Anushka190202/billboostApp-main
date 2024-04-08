package com.example.billboostapp.clickInterface

import com.example.billboostapp.DataModels.Clients

interface ClientListInterface {
    fun onUpdate(clients: Clients)
    fun onDelete(clients: Clients)
}