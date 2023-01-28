package com.example.uberapp_tim22.DTO;

import java.util.Date;

public class MessageDTO {

        private Long id;
        private Date timeOfSending;
        private Long senderId;
        private Long receiverId;
        private String message;
        private String type;
        private Long rideId;

        public MessageDTO() {
        }

        public MessageDTO(Long id, Date timeOfSending, Long senderId, Long receiverId, String message, String type, Long rideId) {
            this.id = id;
            this.timeOfSending = timeOfSending;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.message = message;
            this.type = type;
            this.rideId = rideId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Date getTimeOfSending() {
            return timeOfSending;
        }

        public void setTimeOfSending(Date timeOfSending) {
            this.timeOfSending = timeOfSending;
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public Long getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Long receiverId) {
            this.receiverId = receiverId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getRideId() {
            return rideId;
        }

        public void setRideId(Long rideId) {
            this.rideId = rideId;
        }
    }

