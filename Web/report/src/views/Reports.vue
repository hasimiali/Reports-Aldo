<template>
  <div class="reports-container">
    <h1>Reports</h1>
    <table class="reports-table">
      <thead>
        <tr>
          <th @click="sortReports('timestamp')">id</th> <!-- Make header clickable for sorting -->
          <th>Image</th>
          <th>Description</th>
          <th>Urgency</th>
          <th>Status</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(report, index) in sortedReports" :key="index">
          <td class="report-number">{{ index + 1 }}</td>
          <td><img :src="report.imageUrl" :alt="'Image ' + (index + 1)" class="report-image"></td>
          <td class="report-description">{{ report.description }}</td>
          <td :class="`urgency-${report.urgency.toLowerCase()}`">{{ report.urgency }}</td>
          <td>
            <select v-model="report.status" @change="updateStatus(index, report)">
              <option value="not done">Not Done</option>
              <option value="in progress">In Progress</option>
              <option value="done">Done</option>
            </select>
          </td>
          <td>
            <button @click="deleteReport(index, report.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { db } from '../firebase'; // Import db from firebase.js
import { collection, getDocs, updateDoc, doc, deleteDoc } from 'firebase/firestore';

const reports = ref([]);
const sortKey = ref('timestamp');
const sortOrder = ref('desc'); // Set initial sort order to 'desc' to show latest first

const fetchReports = async () => {
  try {
    const querySnapshot = await getDocs(collection(db, 'images'));
    reports.value = querySnapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
  } catch (error) {
    console.error("Error fetching reports: ", error);
  }
};

const updateStatus = async (index, report) => {
  try {
    const reportRef = doc(db, 'images', report.id);
    await updateDoc(reportRef, {
      status: report.status
    });
    console.log(`Updated report ${index} to status: ${report.status}`);
  } catch (error) {
    console.error("Error updating status: ", error);
  }
};

const deleteReport = async (index, reportId) => {
  try {
    const reportRef = doc(db, 'images', reportId);
    await deleteDoc(reportRef);
    reports.value.splice(index, 1); // Remove the deleted report from the local array
    console.log(`Deleted report ${reportId}`);
  } catch (error) {
    console.error("Error deleting report: ", error);
  }
};

const sortReports = (key) => {
  if (sortKey.value === key) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortKey.value = key;
    sortOrder.value = 'asc';
  }
};

const sortedReports = computed(() => {
  return reports.value.slice().sort((a, b) => {
    if (sortOrder.value === 'asc') {
      return new Date(a[sortKey.value]) - new Date(b[sortKey.value]);
    } else {
      return new Date(b[sortKey.value]) - new Date(a[sortKey.value]);
    }
  });
});

onMounted(() => {
  fetchReports();
});
</script>

<style scoped>
.reports-container {
  max-width: 100%; /* Remove the max-width restriction */
  margin: 0; /* Remove margin */
  padding: 0; /* Remove padding */
  color: #333; /* Dark gray text color */
}

h1 {
  text-align: center;
  margin-bottom: 40px;
  color: #000; /* Black text color */
}

.reports-table {
  width: 100%;
  border-collapse: collapse;
}

.reports-table th, .reports-table td {
  border: 1px solid #ddd; /* Light gray border */
  padding: 12px;
  text-align: left;
}

.reports-table th {
  background-color: #f0f0f0; /* Light gray background */
  color: #000; /* Black text color */
  cursor: pointer; /* Indicate clickable headers */
}

.report-number, .report-description {
  color: #000; /* Black color for number and description */
}

.report-image {
  width: 150px; /* Set fixed width */
  height: 150px; /* Set fixed height */
  object-fit: cover; /* Ensure images are cropped to fit within the fixed dimensions */
  border-radius: 8px;
}

.urgency-high {
  color: #ff0000; /* Red color */
  font-weight: bold;
}

.urgency-medium {
  color: #ffa500; /* Orange color */
  font-weight: bold;
}

.urgency-low {
  color: #008000; /* Green color */
  font-weight: bold;
}

select {
  padding: 8px; /* Increased padding for better usability */
  border-radius: 4px;
  border: 1px solid #ccc;
  background-color: #fff;
  color: #333;
  cursor: pointer;
  transition: background-color 0.3s;
}

select:hover {
  background-color: #f1f1f1;
}

button {
  padding: 8px 12px;
  border-radius: 4px;
  border: none;
  background-color: #333; /* Dark gray background */
  color: #fff; /* White text color */
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #555; /* Slightly lighter gray */
}

@media (max-width: 600px) {
  .reports-table th, .reports-table td {
    padding: 8px; /* Reduced padding for smaller screens */
  }

  .report-image {
    width: 100px; /* Reduced width for smaller screens */
    height: 100px; /* Reduced height for smaller screens */
  }
}
</style>
