using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by client to authenticate with the server
    /// </summary>
    public class MitchLoginRequest
    {
        /// <summary>
        /// CompID assigned to the client
        /// </summary>
        public string Username { get; set; }

        /// <summary>
        /// Password assigned to the CompID
        /// </summary>
        public string Password { get; set; }

        /// <summary>
        /// Original IP Address the user is running from (the server IP Address for audit purpose)
        /// </summary>
        public string IpAddress { get; set; }

        /// <summary>
        /// Identifier of the solution that has been certificated against A5X trading platform (ex.: Trading System v1.0)
        /// </summary>
        public string CertifiedSolution { get; set; }

        public static void Parse(MitchLoginRequest msg, Span<byte> data)
        {
            int offset = 0;
            msg.Username = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
            msg.Password = Encoding.ASCII.GetString(data.Slice(offset, 10)).TrimEnd();
            offset += 10;
            msg.IpAddress = Encoding.ASCII.GetString(data.Slice(offset, 15)).TrimEnd();
            offset += 15;
            msg.CertifiedSolution = Encoding.ASCII.GetString(data.Slice(offset, 44)).TrimEnd();
            offset += 44;
        }

        public static void Encode(MitchLoginRequest msg, Span<byte> data)
        {
            int offset = 0;
            var usernameBytes = Encoding.ASCII.GetBytes(msg.Username.PadRight(6));
            usernameBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
            var passwordBytes = Encoding.ASCII.GetBytes(msg.Password.PadRight(10));
            passwordBytes.AsSpan(0, 10).CopyTo(data.Slice(offset, 10));
            offset += 10;
            var ipAddressBytes = Encoding.ASCII.GetBytes(msg.IpAddress.PadRight(15));
            ipAddressBytes.AsSpan(0, 15).CopyTo(data.Slice(offset, 15));
            offset += 15;
            var certifiedSolutionBytes = Encoding.ASCII.GetBytes(msg.CertifiedSolution.PadRight(44));
            certifiedSolutionBytes.AsSpan(0, 44).CopyTo(data.Slice(offset, 44));
            offset += 44;
        }
    }
}
