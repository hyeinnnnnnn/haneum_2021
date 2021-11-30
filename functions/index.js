// The Cloud Functions for Firebase SDK to create Cloud Functions and set up triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access Firestore.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
// Take the text parameter passed to this HTTP endpoint and insert it into
// Firestore under the path /messages/:documentId/original

exports.messagePhone = functions.firestore.document('/Root/Suspected/Info/{documentId}')
    .onCreate((snap, context) => {
      const Tok = 'c_Qfa7ifSjiMkKOb6v95Xy:APA91bFngjXCRNLwciDSZO3z0Dm0XT2GXgNy1y9V-MhLvbfWxvDOb3fjFEH_HYCoXNL_bQsdCpjNlj_TYDC_yW63iyJ2idlR2IoprTDJl-4BdHJgq8K1BdkQbeDuFFa6pOgHo3P0ssmm';
      const payload = {
        notification: {
          title: '확진 의심자',
          body: '확진 의심자가 입장했습니다!!!!'
        }
      };
      admin.messaging().sendToDevice(Tok, payload);
      pusing.pushMessage(Tok,payload);
});
