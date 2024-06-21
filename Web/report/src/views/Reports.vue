<template>
  <div class="reports-container">
    <h1>Reports</h1>
    <div v-for="(report, index) in reports" :key="index" class="report-card">
      <div class="report-info">
        <h2>{{ report.title }}</h2>
        <img :src="report.imageUrl" alt="Report Image" class="report-image">
      </div>
      <div class="report-status" >
        <p>{{ report.description }}</p>
        <p><strong>Urgency:</strong> {{ report.urgency }}</p>
        <label for="status">Status:</label>
        <select v-model="report.status" @change="updateStatus(index, report)">
          <option value="not done">Not Done</option>
          <option value="in progress">In Progress</option>
          <option value="done">Done</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { db } from '../firebase'; // Import db from firebase.js
import { collection, getDocs, updateDoc, doc } from 'firebase/firestore';

const reports = ref([]);

const fetchReports = async () => {
  const querySnapshot = await getDocs(collection(db, 'images'));
  querySnapshot.forEach((doc) => {
    reports.value.push({ id: doc.id, ...doc.data() });
  });
};

const updateStatus = async (index, report) => {
  const reportRef = doc(db, 'images', report.id);
  await updateDoc(reportRef, {
    status: report.status
  });
  console.log(`Updated report ${index} to status: ${report.status}`);
};

onMounted(() => {
  fetchReports();
});
</script>

<style scoped>
.reports-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  color: #333;
}

.report-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  background-color: #fff;
}

.report-info {
  flex: 1;
  margin-right: 20px;
}

.report-image {
  max-width: 100px;
  border-radius: 8px;
}

.report-status {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.report-status label {
  margin-bottom: 8px;
  color: #333;
}

.report-status select {
  padding: 5px;
  border-radius: 4px;
  border: 1px solid #ccc;
  background-color: #fff;
  color: #333;
}
</style>
