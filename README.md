📦 Warehouse Platform API
👤 Developer Info

Waleed Alharbi
Responsible for implementing endpoints related to RequestController and ReviewController.

Sahar Turki
Responsible for implementing endpoints related to Warehouse, Supplier, Provider, Complaint, and Admin.

Khaled Alshehri
Responsible for implementing booking notifications, fines, booking extensions, and employee assignments.


✅ Endpoints Implemented by Waleed Alharbi
📍 RequestController
POST /api/v1/request/add/{clientId}/{warehouseId}
➤ Add a new request.

PUT /api/v1/request/update/{id}
➤ Update an existing request.

DELETE /api/v1/request/delete/{id}
➤ Delete a request.

GET /api/v1/request/search-by-date/{startDate}/{endDate}
➤ Search for requests within a specific date range.

PUT /api/v1/request/check-availability/{startDate}/{endDate}
➤ Check availability for a date range.

📍 ReviewController

GET /api/v1/review/get-reviews-by-warehouse/{wareHouseId}
➤ Reviews by warehouse.

GET /api/v1/review/average-rating/{wareHouseId}
➤ Average rating per warehouse.

GET /api/v1/review/top-warehouses-by-rating
➤ Top-rated warehouses.

GET /api/v1/review/get-reviews-by-client/{supplierId}
➤ Reviews by supplier.

GET /api/v1/review/get-reviews-by-warehouse-sorted/{wareHouseId}
➤ Reviews sorted by date.

✅ Endpoints Implemented by Sahar Turki
📍 WarehouseController
GET /api/v1/warehouse/most-used/{storeSize}
➤ Most used warehouses.

📍 SupplierController
GET /api/v1/supplier/get-all-complains/{id}
➤ All supplier complaints.

GET /api/v1/supplier/approved-complaints/{id}
➤ Approved complaints for supplier.

📍 SupplierComplaintController
PUT /api/v1/supplier-complaint/file-complain-on-provider/{supplierId}/{requestId}
➤ File complaint on provider.

📍 StorageProviderController
PUT /api/v1/provider/renew-licence/{id}
➤ Renew license.

GET /api/v1/provider/approved-complaints/{id}
➤ Approved provider complaints.

GET /api/v1/provider/get-all-complains/{id}
➤ All provider complaints.

📍 ReviewController (Additional)
GET /api/v1/review/get-average-provider-reviews/{providerId}
➤ Provider average rating.

📍 ProviderComplaintController
PUT /api/v1/provider-complaint/file-complain-on-supplier/{supplierId}/{requestId}
➤ Complaint on supplier.

📍 AdminController
PUT /api/v1/admin/validate-licence/{adminId}/{providerId}
➤ Validate license.

PUT /api/v1/admin/approve-supplier-complain/{adminId}/{supplierId}/{complainId}
➤ Approve supplier complaint.

PUT /api/v1/admin/approve-supplier-complain/{adminId}/{providerId}/{requestId}
➤ Alternate approval path.

PUT /api/v1/admin/approve-provider-licence-renew/{adminId}/{providerId}
➤ Approve license renewal.

✅ Endpoints Implemented by Khaled Alshehri
📍 ClientController
POST /api/v1/client/notify-booking-end/{requestId}
➤ Notify supplier when booking ends.

POST /api/v1/client/late-fine/{requestId}
➤ Apply fine for late booking.

PUT /api/v1/client/extend-booking/{requestId}/{newEndDate}
➤ Extend booking end date.

POST /api/v1/client/reminder-before-end/{requestId}
➤ Send reminder 10 days before booking ends.

📍 AdminController
POST /api/v1/admin/notify-license-expired/{providerId}/{adminId}
➤ Notify provider of license expiration.

📍 EmployeeController
PUT /api/v1/employee/assign/{employeeId}/{providerId}
➤ Assign employee to provider.

